package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.apply.Preference;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.apply.response.ApplyGetResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyListResponse;
import kahlua.KahluaProject.dto.ticket.response.TicketUpdateResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.dto.ticket.response.TicketListResponse;
import kahlua.KahluaProject.service.ApplyService;
import kahlua.KahluaProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자", description = "관리자 페이지 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {

    private final ApplyService applyService;
    private final TicketService ticketService;

    @GetMapping("/apply/all")
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

    @GetMapping("/apply")
    @Operation(summary = "지원자 리스트 세션별 조회", description = "1지망 -> 2지망 악기 순서로 정렬된 지원자 리스트를 조회합니다")
    public ApiResponse<ApplyListResponse> getApplyListByPreference(@AuthenticationPrincipal AuthDetails authDetails, @RequestParam(name = "preference") Preference preference) {
        ApplyListResponse applyListResponse = applyService.getApplyListByPreference(authDetails.user(), preference);
        return ApiResponse.onSuccess(applyListResponse);
    }

    @GetMapping("/tickets")
    @Operation(summary = "전체 티켓 리스트 조회", description = "sort-by에 정의된 티켓 속성 기준으로 정렬된 전체 티켓 리스트를 조회합니다 </br> sort-by를 쿼리에 포함시키지 않은 경우 최신순으로 정렬합니다")
    public ApiResponse<TicketListResponse> getTicketList(@AuthenticationPrincipal AuthDetails authDetails,
                                                         @RequestParam(name = "sort-by", required = false) String sortBy) {
        TicketListResponse ticketListResponse = ticketService.getTicketList(authDetails.user(), sortBy);
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/tickets/general")
    @Operation(summary = "일반 티켓 리스트 조회", description = "sort-by에 정의된 티켓 속성 기준으로 정렬된 일반 티켓 리스트를 조회합니다 </br> sort-by를 쿼리에 포함시키지 않은 경우 최신순으로 정렬합니다")
    public ApiResponse<TicketListResponse> getGeneralTicketList(@AuthenticationPrincipal AuthDetails authDetails,
                                                                @RequestParam(name = "sort-by", required = false) String sortBy) {
        TicketListResponse ticketListResponse = ticketService.getGeneralTicketList(authDetails.user(), sortBy);
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/tickets/freshman")
    @Operation(summary = "신입생 티켓 리스트 조회", description = "sort-by에 정의된 티켓 속성 기준으로 정렬된 신입생 티켓 리스트를 조회합니다 </br> sort-by를 쿼리에 포함시키지 않은 경우 최신순으로 정렬합니다")
    public ApiResponse<TicketListResponse> getFreshmanTicketList(@AuthenticationPrincipal AuthDetails authDetails,
                                                                 @RequestParam(name = "sort-by", required = false) String sortBy) {
        TicketListResponse ticketListResponse = ticketService.getFreshmanTicketList(authDetails.user(), sortBy);
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @PatchMapping("/{ticketId}/ticket-complete")
    @Operation(summary = "티켓 결제 완료", description = "티켓 결제를 완료한 뒤 어드민 페이지에서 결제 완료를 클릭하는 경우 결제 완료로 상태가 변합니다.")
    public ApiResponse<TicketUpdateResponse> completePaymentForm(@PathVariable(name = "ticketId") Long ticketId, @AuthenticationPrincipal AuthDetails authDetails) {
        TicketUpdateResponse ticketUpdateResponse = ticketService.completePayment(authDetails.user(), ticketId);
        return ApiResponse.onSuccess(ticketUpdateResponse);
    }

    @PatchMapping("/{ticketId}/cancel-complete")
    @Operation(summary = "티켓 취소 완료", description = "티켓 환불을 완료한 뒤 어드민 페이지에서 취소 완료를 클릭하는 경우 취소가 완료로 상태가 변합니다.")
    public ApiResponse<TicketUpdateResponse> completeCancelForm(@PathVariable(name = "ticketId") Long ticketId, @AuthenticationPrincipal AuthDetails authDetails) {
        TicketUpdateResponse ticketUpdateResponse = ticketService.completeCancelTicket(authDetails.user(), ticketId);
        return ApiResponse.onSuccess(ticketUpdateResponse);
    }
}
