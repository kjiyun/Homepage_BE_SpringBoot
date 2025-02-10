package kahlua.KahluaProject.dto.ticketInfo.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


public class PerformanceRes {

    @Getter
    @Builder
    @AllArgsConstructor
    public static class performanceListDto{
        private List<performanceDto> performances;
        @Schema(description = "다음 커서 값 (커서는 공연 id임)")
        private Long nextcursor;
        @Schema(description = "다음 페이지가 있을 경우 값=1, 없을 경우 = 0")
        private boolean hasNext;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class performanceDto {

        @Schema(description = "아이디")
        Long ticketInfoId;

        @Schema(description = "공연 제목")
        String title;

        @Schema(description = "공연 포스터 이미지")
        String posterUrl;

        @Schema(description = "공연 상태")
        PerformanceStatus status;
    }
}
