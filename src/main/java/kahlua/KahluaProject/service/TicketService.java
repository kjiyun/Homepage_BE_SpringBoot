package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.TicketConverter;
import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.*;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ParticipantsRepository;
import kahlua.KahluaProject.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final ParticipantsRepository participantsRepository;

    @Transactional
    public TicketCreateResponse createTicket(TicketCreateRequest ticketCreateRequest) {

        String reservationId = uniqueReservationId();

        Ticket savedTicket = TicketConverter.toTicket(ticketCreateRequest, reservationId);
        ticketRepository.save(savedTicket);

        // service 혹은 converter
        List<Participants> members = ticketCreateRequest.getMembers().stream()
                .map(memberRequest -> Participants.builder()
                        .name(memberRequest.getName())
                        .phone_num(memberRequest.getPhone_num())
                        .ticket(savedTicket)
                        .build())
                .collect(Collectors.toList());

        participantsRepository.saveAll(members);

        TicketCreateResponse ticketCreateResponse = TicketConverter.toTicketCreateResponse(savedTicket, reservationId, members);
        return ticketCreateResponse;
    }

    @Transactional
    public TicketGetResponse viewTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SESSION_UNAUTHORIZED));

        List<Participants> participants = participantsRepository.findByTicket(ticket);

        return TicketConverter.toTicketGetResponse(ticket, participants);
    }

    // 어드민 페이지 티켓 리스트 조회
    public TicketListResponse getTicketList(User user) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Ticket> tickets = ticketRepository.findAll();
        List<TicketItemResponse> ticketItemResponses = new ArrayList<>();

        for (Ticket ticket : tickets) {
            // 일반티켓
            // 전공, 뒷풀이 참석 여부 null
            if (ticket.getType() == Type.GENERAL){
                TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                        .id(ticket.getId())
                        .status(ticket.getStatus())
                        .reservation_id(ticket.getReservationId())
                        .buyer(ticket.getBuyer())
                        .phone_num(ticket.getPhone_num())
                        .total_ticket(participantsRepository.countByTicket_Id(ticket.getId()) + 1)
                        .build();

                ticketItemResponses.add(ticketItemResponse);
            }
            // 신입생티켓
            // 티켓 매수 1 고정
            else if (ticket.getType() == Type.FRESHMAN) {
                TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                        .id(ticket.getId())
                        .status(ticket.getStatus())
                        .reservation_id(ticket.getReservationId())
                        .buyer(ticket.getBuyer())
                        .phone_num(ticket.getPhone_num())
                        .total_ticket(1)
                        .major(ticket.getMajor())
                        .meeting(ticket.getMeeting())
                        .build();

                ticketItemResponses.add(ticketItemResponse);
            }
        }

        TicketListResponse ticketListResponse = TicketListResponse.builder()
                .total(countTickets(ticketItemResponses))
                .tickets(ticketItemResponses)
                .build();

        return ticketListResponse;
    }

    // 일반 티켓 리스트 조회
    public TicketListResponse getGeneralTicketList(User user) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Ticket> tickets = ticketRepository.findAllByType(Type.GENERAL);
        List<TicketItemResponse> ticketItemResponses = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                    .id(ticket.getId())
                    .status(ticket.getStatus())
                    .reservation_id(ticket.getReservationId())
                    .buyer(ticket.getBuyer())
                    .phone_num(ticket.getPhone_num())
                    .total_ticket(participantsRepository.countByTicket_Id(ticket.getId()) + 1)
                    .build();

            ticketItemResponses.add(ticketItemResponse);
        }

        TicketListResponse ticketListResponse = TicketListResponse.builder()
                .total(countTickets(ticketItemResponses))
                .tickets(ticketItemResponses)
                .build();

        return ticketListResponse;
    }

    // 신입생 티켓 리스트 조회
    public TicketListResponse getFreshmanTicketList(User user) {

        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        List<Ticket> tickets = ticketRepository.findAllByType(Type.FRESHMAN);
        List<TicketItemResponse> ticketItemResponses = new ArrayList<>();

        for (Ticket ticket : tickets) {
            TicketItemResponse ticketItemResponse = TicketItemResponse.builder()
                    .id(ticket.getId())
                    .status(ticket.getStatus())
                    .reservation_id(ticket.getReservationId())
                    .buyer(ticket.getBuyer())
                    .phone_num(ticket.getPhone_num())
                    .total_ticket(1)
                    .major(ticket.getMajor())
                    .meeting(ticket.getMeeting())
                    .build();

            ticketItemResponses.add(ticketItemResponse);
        }

        TicketListResponse ticketListResponse = TicketListResponse.builder()
                .total(countTickets(ticketItemResponses))
                .tickets(ticketItemResponses)
                .build();

        return ticketListResponse;
    }

    //예약번호가 기존 예약번호와 일치하면 안되므로 중복되는지 확인하는 기능
    public String uniqueReservationId() {
        String reservationId;
        do {
            reservationId = generateReservationId();
        } while(ticketRepository.existsByReservationId(reservationId));

        return reservationId;
    }

    public String generateReservationId() {
        int length = 10;  // 예약 번호 길이
        Random random = new Random();
        List<String> idList = new ArrayList<>();

        for (int i=0; i<length/2; i++) {
            idList.add(String.valueOf(random.nextInt(10)));
        }

        for (int i=0; i<length/2; i++) {
            idList.add(String.valueOf((char) (random.nextInt(26) + 65)));
        }

        Collections.shuffle(idList);

        return String.join("", idList);
    }

    // 티켓 매수 count - 일반 티켓 리스트 조회에 사용
    public Integer countTickets(List<TicketItemResponse> ticketItemResponses) {

        Integer total = 0;
        for (TicketItemResponse ticketItemResponse : ticketItemResponses) {
            total += ticketItemResponse.getTotal_ticket();
        }

        return total;
    }
}
