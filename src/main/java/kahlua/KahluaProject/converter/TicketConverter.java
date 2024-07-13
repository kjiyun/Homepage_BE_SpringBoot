package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.dto.request.ParticipantsCreateRequest;
import kahlua.KahluaProject.dto.request.TicketCreateRequest;
import kahlua.KahluaProject.dto.response.TicketCreateResponse;

public class TicketConverter {

    public static Ticket toTicket(TicketCreateRequest ticketCreateRequest, String reservation_id) {
        Ticket ticket;
        ticket = Ticket.builder()
                .buyer(ticketCreateRequest.getBuyer())
                .phone_num(ticketCreateRequest.getPhone_num())
                .reservation_id(reservation_id)
                .type(ticketCreateRequest.getType())
                .major(ticketCreateRequest.getMajor())
                .student_id(ticketCreateRequest.getStudent_id())
                .meeting(ticketCreateRequest.getMeeting())
                .build();

        for (ParticipantsCreateRequest participantsCreateRequest : ticketCreateRequest.getMembers()) {
            Participants participants = new Participants();
            participants.setName(participantsCreateRequest.getName());
            participants.setPhone_num(participantsCreateRequest.getPhone_num());
            ticket.addMembers(participants);
        }


        return ticket;
    }

    public static TicketCreateResponse toTicketCreateResponse(Ticket ticket, String reservation_id) {
        return TicketCreateResponse.builder()
                .id(ticket.getId())
                .buyer(ticket.getBuyer())
                .phone_num(ticket.getPhone_num())
                .reservation_id(reservation_id)
                .type(ticket.getType())
                .major(ticket.getMajor())
                .student_id(ticket.getStudent_id())
                .meeting(ticket.getMeeting())
                .members(ticket.getMembers())
                .status(ticket.getStatus())
                .build();
    }
}
