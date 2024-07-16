package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.TicketConverter;
import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.dto.request.ParticipantsCreateRequest;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.ParticipantsResponse;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;
import kahlua.KahluaProject.dto.response.TicketGetResponse;
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

    @Transactional
    public TicketGetResponse viewTicket(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SESSION_UNAUTHORIZED));

        List<Participants> participants = participantsRepository.findByTicket(ticket);

        return TicketConverter.toTicketGetResponse(ticket, participants);
    }

}
