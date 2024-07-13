package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
