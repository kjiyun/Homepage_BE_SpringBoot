package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByReservationId(String reservationId);
}
