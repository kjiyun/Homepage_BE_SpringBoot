package kahlua.KahluaProject.service;

import kahlua.KahluaProject.converter.KahluaInfoConverter;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.dto.kahluaInfo.request.LeaderInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.response.LeaderInfoResponse;
import kahlua.KahluaProject.repository.kahluaInfo.LeaderInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class KahluaInfoService {
    private final LeaderInfoRepository leaderInfoRepository;
    private final MailCacheService mailCacheService;

    @Transactional
    public LeaderInfoResponse updateLeaderInfo(LeaderInfoRequest request) {
        LeaderInfo info = getOrCreateLeaderInfo();
        LeaderInfo newInfo = KahluaInfoConverter.toLeaderInfo(request);

        info.update(newInfo);
        mailCacheService.updateLeaderInfo(info);
        return KahluaInfoConverter.toLeaderDto(info);
    }

    public LeaderInfoResponse getLeaderInfo() {
        LeaderInfo info = getOrCreateLeaderInfo();
        return KahluaInfoConverter.toLeaderDto(info);
    }

    private LeaderInfo getOrCreateLeaderInfo() {
        return leaderInfoRepository.findTopByOrderByIdDesc()
                .orElseGet(() -> leaderInfoRepository.save(
                        KahluaInfoConverter.createEmptyLeaderInfo()
                ));
    }
}
