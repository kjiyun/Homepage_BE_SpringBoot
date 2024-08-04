package kahlua.KahluaProject.repository.ticket;

import kahlua.KahluaProject.domain.ticket.Ticket;

import java.util.List;

public interface TicketCustomRepository {

    List<Ticket> findAllOrderByStatus();
}
