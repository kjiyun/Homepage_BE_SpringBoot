package kahlua.KahluaProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class TicketListResponse {

    private Integer total;
    private List<TicketItemResponse> tickets;
}
