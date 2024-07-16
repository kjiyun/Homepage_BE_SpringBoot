package kahlua.KahluaProject.controller;

import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;
import kahlua.KahluaProject.dto.response.TicketGetResponse;
import kahlua.KahluaProject.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/tickets")
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ApiResponse<TicketCreateResponse> createTicketFrom(@RequestBody TicketCreateRequest ticketCreateRequest) {
        TicketCreateResponse ticketCreateResponse = ticketService.createTicket(ticketCreateRequest);
        return ApiResponse.onSuccess(ticketCreateResponse);
    }

    @GetMapping("/{ticketId}")
    public ApiResponse<TicketGetResponse> viewTicketForm(@PathVariable(name = "ticketId") Long ticketId) {
        TicketGetResponse ticketGetResponse = ticketService.viewTicket(ticketId);
        return ApiResponse.onSuccess(ticketGetResponse);
    }
}
