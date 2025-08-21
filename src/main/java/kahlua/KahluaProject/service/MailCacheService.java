package kahlua.KahluaProject.service;

import jakarta.annotation.PostConstruct;
import kahlua.KahluaProject.domain.applyInfo.ApplyInfo;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.domain.performance.Performance;
import kahlua.KahluaProject.repository.ApplyInfoRepository;
import kahlua.KahluaProject.repository.kahluaInfo.LeaderInfoRepository;
import kahlua.KahluaProject.repository.performance.PerformanceRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCacheService {
    private final LeaderInfoRepository leaderInfoRepository;
    private final ApplyInfoRepository applyInfoRepository;
    private final PerformanceRepository performanceRepository;

    // getter
    @Getter
    private LeaderInfo leaderInfo;
    @Getter
    private ApplyInfo applyInfo;
    @Getter
    private Performance performance;

    @PostConstruct
    public void init() {
        this.leaderInfo = leaderInfoRepository.findTopByOrderByIdDesc().orElse(null);
        this.applyInfo = applyInfoRepository.findTopByOrderByIdDesc().orElse(null);
        this.performance = performanceRepository.findTopByOrderByIdDesc().orElse(null);
    }

    // setter
    public void updateLeaderInfo(LeaderInfo leaderInfo) {
        this.leaderInfo = leaderInfo;
    }

    public void updateApplyInfo(ApplyInfo applyInfo) {
        this.applyInfo = applyInfo;
    }

    public void updatePerformance(Performance performance) {
        this.performance = performance;
    }
}