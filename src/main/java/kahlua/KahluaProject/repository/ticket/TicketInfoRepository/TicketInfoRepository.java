package kahlua.KahluaProject.repository.ticket.TicketInfoRepository;

import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long>,TicketInfoCustomRepository {
}
