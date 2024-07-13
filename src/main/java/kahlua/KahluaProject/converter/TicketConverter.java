package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;

public class TicketConverter {

    public static Ticket toTicket(TicketCreateRequest ticketCreateRequest) {
        return Ticket.builder()
                .buyer(ticketCreateRequest.getBuyer())
                .phone_num(ticketCreateRequest.getPhone_num())
                .type(ticketCreateRequest.getType())
                .major(ticketCreateRequest.getMajor())
                .student_id(ticketCreateRequest.getStudent_id())
                .meeting(ticketCreateRequest.getMeeting())
                .members(ticketCreateRequest.getMembers())
                .build();
    }

    public static TicketCreateResponse toTicketCreateResponse(Ticket ticket) {
        return TicketCreateResponse.builder()
                .id(ticket.getId())
                .buyer(ticket.getBuyer())
                .phone_num(ticket.getPhone_num())
                .reservation_id(ticket.getReservation_id())
                .type(ticket.getType())
                .major(ticket.getMajor())
                .student_id(ticket.getStudent_id())
                .meeting(ticket.getMeeting())
                .members(ticket.getMembers())
                .status(ticket.getStatus())
                .build();
    }
}
