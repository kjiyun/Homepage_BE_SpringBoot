package kahlua.KahluaProject.repository.ticket.PerformanceRepository;

import kahlua.KahluaProject.domain.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceCustomRepository {
}
