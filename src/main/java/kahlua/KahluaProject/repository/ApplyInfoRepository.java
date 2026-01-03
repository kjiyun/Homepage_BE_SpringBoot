package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.applyInfo.ApplyInfo;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplyInfoRepository extends JpaRepository<ApplyInfo, Long> {
        Optional<ApplyInfo> findTopByOrderByIdDesc();
}
