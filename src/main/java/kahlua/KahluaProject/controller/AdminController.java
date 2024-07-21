package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.response.ApplyGetResponse;
import kahlua.KahluaProject.dto.response.ApplyListResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.dto.response.TicketListResponse;
import kahlua.KahluaProject.service.ApplyService;
import kahlua.KahluaProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자", description = "관리자 페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {

    private final ApplyService applyService;
    private final TicketService ticketService;

    @GetMapping("/apply")
    @Operation(summary = "지원자 리스트 조회", description = "id 기준으로 정렬된 지원자 리스트를 조회합니다")
    public ApiResponse<ApplyListResponse> getApplyList(@AuthenticationPrincipal AuthDetails authDetails) {
        ApplyListResponse applyListResponse = applyService.getApplyList(authDetails.user());
        return ApiResponse.onSuccess(applyListResponse);
    }

    @GetMapping("/apply/{applyId}")
    @Operation(summary = "지원자 상세정보 조회", description = "지원자 상세정보를 조회합니다")
    public ApiResponse<ApplyGetResponse> getApplyDetail(@PathVariable Long applyId, @AuthenticationPrincipal AuthDetails authDetails) {
        if(authDetails.getUser().getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        ApplyGetResponse applyGetResponse = applyService.getApply(applyId);
        return ApiResponse.onSuccess(applyGetResponse);
    }

    @GetMapping("/tickets")
    @Operation(summary = "전체 티켓 리스트 조회", description = "id 기준으로 정렬된 전체 티켓 리스트를 조회합니다")
    public ApiResponse<TicketListResponse> getTicketList(@AuthenticationPrincipal AuthDetails authDetails) {
        TicketListResponse ticketListResponse = ticketService.getTicketList(authDetails.user());
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/tickets/general/")
    @Operation(summary = "일반 티켓 리스트 조회", description = "id 기준으로 정렬된 일반 티켓 리스트를 조회합니다")
    public ApiResponse<TicketListResponse> getGeneralTicketList(@AuthenticationPrincipal AuthDetails authDetails) {
        TicketListResponse ticketListResponse = ticketService.getGeneralTicketList(authDetails.user());
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/tickets/freshman/")
    @Operation(summary = "신입생 티켓 리스트 조회", description = "id 기준으로 정렬된 신입생 티켓 리스트를 조회합니다")
    public ApiResponse<TicketListResponse> getFreshmanTicketList(@AuthenticationPrincipal AuthDetails authDetails) {
        TicketListResponse ticketListResponse = ticketService.getFreshmanTicketList(authDetails.user());
        return ApiResponse.onSuccess(ticketListResponse);
    }
}
