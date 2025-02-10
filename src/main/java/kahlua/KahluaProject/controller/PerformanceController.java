package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.ticketInfo.response.PerformanceRes;
import kahlua.KahluaProject.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "공연 페이지", description = "공연페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping
    @Operation(summary = "공연 리스트 조회 API", description = "공연들을 조회하는 API입니다.")
    public ApiResponse<PerformanceRes.performanceListDto> getPerformances(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "8") int limit){
        return ApiResponse.onSuccess(performanceService.getPerformances(cursor, limit));
    }


}
