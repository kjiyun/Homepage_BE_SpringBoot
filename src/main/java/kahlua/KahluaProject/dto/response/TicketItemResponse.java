package kahlua.KahluaProject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kahlua.KahluaProject.domain.ticket.Meeting;
import kahlua.KahluaProject.domain.ticket.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TicketItemResponse {

    @Schema(description = "티켓 id")
    private Long id;

    @Schema(description = "결제상태")
    private Status status;

    @Schema(description = "예약번호")
    private String reservation_id;

    @Schema(description = "티켓 구매자 이름")
    private String buyer;

    @Schema(description = "티켓 구매자 전화번호")
    private String phone_num;

    @Schema(description = "티켓 매수")
    private Integer total_ticket;

    @Schema(description = "티켓 구매자 전공")
    private String major;

    @Schema(description = "뒷풀이 참여여부")
    private Meeting meeting;
}
