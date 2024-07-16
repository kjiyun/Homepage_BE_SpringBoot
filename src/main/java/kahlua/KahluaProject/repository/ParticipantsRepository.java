package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ParticipantsRepository extends JpaRepository<Participants, Long> {
    List<Participants> findByTicket(Ticket ticket);
}
