package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.reservation.response.ReservationListResponse;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "마이페이지", description = "마이페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/my-page")
public class MyPageController {

    private final ReservationService reservationService;

    @GetMapping("/reservation")
    @Operation(summary = "사용자 예약내역 조회", description = "사용자의 전체 예약내역을 예약 날짜 오름차순으로 조회합니다." +
            "<br> 같은 날짜에 대해서는 시작 시간 오름차순으로 정렬합니다.")
    public ApiResponse<ReservationListResponse> getReservationList(@AuthenticationPrincipal AuthDetails authDetails) {

        return ApiResponse.onSuccess(reservationService.getByUser(authDetails.user()));
    }

    @DeleteMapping("/reservation/{reservationId}")
    @Operation(summary = "예약내역 삭제", description = "지정한 예약내역을 삭제합니다.")
    public ApiResponse<?> delete(@PathVariable Long reservationId) {

        reservationService.delete(reservationId);
        return ApiResponse.onSuccess("예약내역 삭제 성공");
    }
}
