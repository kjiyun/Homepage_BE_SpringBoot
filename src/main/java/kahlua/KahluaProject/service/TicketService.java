package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.converter.TicketConverter;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;
import kahlua.KahluaProject.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    @Transactional
    public TicketCreateResponse createTicket(TicketCreateRequest ticketCreateRequest) {

        Ticket ticket = TicketConverter.toTicket(ticketCreateRequest);
        ticketRepository.save(ticket);

        TicketCreateResponse ticketCreateResponse = TicketConverter.toTicketCreateResponse(ticket);
        return ticketCreateResponse;
    }

}
