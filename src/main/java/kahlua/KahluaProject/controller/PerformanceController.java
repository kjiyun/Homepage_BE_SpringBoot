package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.ticketInfo.request.TicketInfoRequest;
import kahlua.KahluaProject.dto.ticketInfo.response.PerformanceRes;
import kahlua.KahluaProject.dto.ticketInfo.response.TicketInfoResponse;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "공연 페이지", description = "공연페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/performances")
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping
    @Operation(summary = "공연 리스트 조회 API", description = "공연들을 조회하는 API입니다.")
    public ApiResponse<PerformanceRes.performanceListDto> getPerformances(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "8") int limit){
        return ApiResponse.onSuccess(performanceService.getPerformances(cursor, limit));
    }

    @GetMapping("/{ticketInfoId}")
    @Operation(summary = "공연 상세정보 조회 API", description = "특정 공연의 상세정보를 조회하는 API입니다.")
    public ApiResponse<PerformanceRes.performanceInfoDto> getPerformanceInfo(@PathVariable Long ticketInfoId){
        return ApiResponse.onSuccess(performanceService.getPerformanceInfo(ticketInfoId));
    }

    @PostMapping("/create")
    @Operation(summary = "공연정보 생성 API", description = "새로운 공연을 생성하는 API입니다.")
    public ApiResponse<TicketInfoResponse> createPerformance(@RequestBody TicketInfoRequest ticketCreateRequest, @AuthenticationPrincipal AuthDetails authDetails){
        TicketInfoResponse ticketinfoResponse = performanceService.createPerformance(ticketCreateRequest, authDetails.user());
        return ApiResponse.onSuccess(ticketinfoResponse);
    }
}

