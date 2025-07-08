package kahlua.KahluaProject.repository.performance;

import kahlua.KahluaProject.domain.performance.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long>, PerformanceCustomRepository {
    Optional<Performance> findTopByOrderByCreatedAtDesc();
}
