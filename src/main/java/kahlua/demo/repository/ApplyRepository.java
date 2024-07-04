package kahlua.demo.repository;

import kahlua.demo.domain.apply.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {
}
