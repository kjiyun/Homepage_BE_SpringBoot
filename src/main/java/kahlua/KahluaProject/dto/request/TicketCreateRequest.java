package kahlua.KahluaProject.dto.request;

import kahlua.KahluaProject.domain.ticket.Meeting;
import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TicketCreateRequest {

    private String buyer;
    private String phone_num;
    private Type type;
    private String major;
    private String studentId;
    private Meeting meeting;
    private List<ParticipantsCreateRequest> members;
}