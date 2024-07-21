package kahlua.KahluaProject.dto.response;

import kahlua.KahluaProject.domain.ticket.Meeting;
import kahlua.KahluaProject.domain.ticket.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TicketItemResponse {

    private Long id;
    private Status status;
    private String reservation_id;
    private String buyer;
    private String phone_num;
    private Integer total_ticket;
    private String major;
    private Meeting meeting;
}
