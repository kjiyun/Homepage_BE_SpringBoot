package kahlua.KahluaProject.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin")
public class AdminController {

    private final ApplyService applyService;
    private final TicketService ticketService;

    @GetMapping("/apply")
    public ApiResponse<ApplyListResponse> getApplyList(@AuthenticationPrincipal AuthDetails authDetails) {
        ApplyListResponse applyListResponse = applyService.getApplyList(authDetails.user());
        return ApiResponse.onSuccess(applyListResponse);
    }

    @GetMapping("/apply/{applyId}")
    public ApiResponse<ApplyGetResponse> getApplyDetail(@PathVariable Long applyId, @AuthenticationPrincipal AuthDetails authDetails) {
        if(authDetails.getUser().getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        ApplyGetResponse applyGetResponse = applyService.getApply(applyId);
        return ApiResponse.onSuccess(applyGetResponse);
    }

    @GetMapping("/tickets")
    public ApiResponse<TicketListResponse> getTicketList() {
        TicketListResponse ticketListResponse = ticketService.getTicketList();
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/tickets/general/")
    public ApiResponse<TicketListResponse> getGeneralTicketList() {
        TicketListResponse ticketListResponse = ticketService.getGeneralTicketList();
        return ApiResponse.onSuccess(ticketListResponse);
    }

    @GetMapping("/tickets/freshman/")
    public ApiResponse<TicketListResponse> getFreshmanTicketList() {
        TicketListResponse ticketListResponse = ticketService.getFreshmanTicketList();
        return ApiResponse.onSuccess(ticketListResponse);
    }
}
