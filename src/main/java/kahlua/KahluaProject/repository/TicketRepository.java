package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticket.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//데이터베이스와의 상호작용을 담당. 데이터 저장, 조회, 수정, 삭제와 같은 작업을 처리
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    boolean existsByReservationId(String reservationId);
    List<Ticket> findAllByOrderByIdDesc();
    List<Ticket> findAllByTypeOrderByIdDesc(Type type);
    Optional<Ticket> findByReservationId(String reservationId);
    List<Ticket> findAllByOrderByBuyerAscIdDesc();
    List<Ticket> findAllByTypeOrderByBuyerAscIdDesc(Type type);
}
