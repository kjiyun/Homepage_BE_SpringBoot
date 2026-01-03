package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.kahluaInfo.LeaderInfo;
import kahlua.KahluaProject.dto.kahluaInfo.request.LeaderInfoRequest;
import kahlua.KahluaProject.dto.kahluaInfo.response.LeaderInfoResponse;

public class KahluaInfoConverter {

    public static LeaderInfo toLeaderInfo(LeaderInfoRequest request) {
        return LeaderInfo.builder()
                .leaderName(request.leaderName())
                .leaderPhoneNum(request.phoneNumber())
                .leaderEmail(request.email())
                .term(request.term())
                .build();
    }

    public static LeaderInfoResponse toLeaderDto(LeaderInfo info) {
        return LeaderInfoResponse.builder()
                .leaderName(info.getLeaderName())
                .phoneNumber(info.getLeaderPhoneNum())
                .email(info.getLeaderEmail())
                .term(info.getTerm())
                .build();
    }

    public static LeaderInfo createEmptyLeaderInfo() {
        return LeaderInfo.builder()
                .leaderName("")
                .leaderPhoneNum("")
                .leaderEmail("")
                .term(0L)
                .build();
    }
}
