package kahlua.KahluaProject.service;

import kahlua.KahluaProject.domain.applyInfo.ApplyInfo;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.domain.performance.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailCacheService {
    private LeaderInfo leaderInfo;
    private ApplyInfo applyInfo;
    private Performance performance;

    @Cacheable(value = "leaderInfo")
    public LeaderInfo getLeaderInfo() {
        return this.leaderInfo;
    }

    @CachePut(value = "leaderInfo")
    public void updateLeaderInfo(LeaderInfo leaderInfo) {
        this.leaderInfo = leaderInfo;
    }

    @Cacheable(value = "applyInfo")
    public ApplyInfo getApplyInfo() {
        return this.applyInfo;
    }

    @CachePut(value = "applyInfo")
    public void updateApplyInfo(ApplyInfo applyInfo) {
        this.applyInfo = applyInfo;
    }

    @Cacheable(value = "performance")
    public Performance getPerformance() {
        return this.performance;
    }

    @CachePut(value = "performance")
    public void updatePerformance(Performance performance) {
        this.performance = performance;
    }
}
