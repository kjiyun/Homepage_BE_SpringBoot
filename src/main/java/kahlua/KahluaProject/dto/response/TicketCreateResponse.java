package kahlua.KahluaProject.dto.response;

import kahlua.KahluaProject.domain.ticket.Meeting;
import kahlua.KahluaProject.domain.ticket.Participants;
import kahlua.KahluaProject.domain.ticket.Status;
import kahlua.KahluaProject.domain.ticket.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class TicketCreateResponse {

    private Long id;
    private String buyer;
    private String phone_num;
    private String reservationId;
    private Type type;
    private String major;
    private String studentId;
    private Meeting meeting;
    private List<Participants> members;
    private Status status;
}
