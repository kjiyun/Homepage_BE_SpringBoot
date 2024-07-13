package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.converter.TicketConverter;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;
import kahlua.KahluaProject.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public TicketCreateResponse createTicket(TicketCreateRequest ticketCreateRequest) {

        String reservation_id = generateReservationId();

        Ticket ticket = TicketConverter.toTicket(ticketCreateRequest, reservation_id);
        ticketRepository.save(ticket);

        TicketCreateResponse ticketCreateResponse = TicketConverter.toTicketCreateResponse(ticket, reservation_id);
        return ticketCreateResponse;
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

}
