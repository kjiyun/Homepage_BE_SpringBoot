package kahlua.KahluaProject.repository.ticket;

import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketInfoRepository extends JpaRepository<TicketInfo, Long> {
    List<TicketInfo> findAllByOrderByIdDesc(Pageable pageable);

    List<TicketInfo> findAllByIdLessThanOrderByIdDesc(Long cursor, Pageable pageable);
}
