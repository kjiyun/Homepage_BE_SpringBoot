package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.dto.request.ParticipantsCreateRequest;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;

public class TicketConverter {

    public static Ticket toTicket(TicketCreateRequest ticketCreateRequest, String reservationId) {
        Ticket ticket;
        ticket = Ticket.builder()
                .buyer(ticketCreateRequest.getBuyer())
                .phone_num(ticketCreateRequest.getPhone_num())
                .reservationId(reservationId)
                .type(ticketCreateRequest.getType())
                .major(ticketCreateRequest.getMajor())
                .studentId(ticketCreateRequest.getStudentId())
                .meeting(ticketCreateRequest.getMeeting())
                .build();


        for (ParticipantsCreateRequest participantsCreateRequest : ticketCreateRequest.getMembers()) {
            Participants participants = Participants.builder()
                    .name(participantsCreateRequest.getName())
                    .phone_num(participantsCreateRequest.getPhone_num())
                    .build();
            ticket.addMembers(participants);
        }

        return ticket;
    }

    public static TicketCreateResponse toTicketCreateResponse(Ticket ticket, String reservationId) {
        return TicketCreateResponse.builder()
                .id(ticket.getId())
                .buyer(ticket.getBuyer())
                .phone_num(ticket.getPhone_num())
                .reservationId(reservationId)
                .type(ticket.getType())
                .major(ticket.getMajor())
                .studentId(ticket.getStudentId())
                .meeting(ticket.getMeeting())
                .members(ticket.getMembers())
                .status(ticket.getStatus())
                .build();
    }
}
