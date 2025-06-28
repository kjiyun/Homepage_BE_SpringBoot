package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.kahluaInfo.AuditionInfo;
import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.dto.kahluaInfo.request.AuditionInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.request.LeaderInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.response.AuditionInfoResponse;
import kahlua.KahluaProject.dto.kahluaInfo.response.LeaderInfoResponse;

public class KahluaInfoConverter {

    public static LeaderInfoResponse toLeaderDto(LeaderInfo info) {
        return LeaderInfoResponse.builder()
                .leaderName(info.getLeaderName())
                .phoneNumber(info.getPhoneNumber())
                .email(info.getEmail())
                .build();
    }

    public static AuditionInfoResponse toAuditionDto(AuditionInfo info) {
        return AuditionInfoResponse.builder()
                .documentStartDate(info.getDocumentStartDate())
                .documentDeadline(info.getDocumentDeadline())
                .vocalVideoDeadline(info.getVocalVideoDeadline())
                .auditionDateTime(info.getAuditionDateTime())
                .announcementDate(info.getAnnouncementDate())
                .build();
    }
}
