package kahlua.KahluaProject.dto.reservation.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record ReservationListResponse(
        @Schema(description = "예약내역 목록")
        List<ReservationResponse> reservationResponseList
) {
}
