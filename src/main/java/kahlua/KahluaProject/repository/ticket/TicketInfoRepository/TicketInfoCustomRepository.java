package kahlua.KahluaProject.repository.ticket.TicketInfoRepository;

import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface TicketInfoCustomRepository {
    List<TicketInfo> findTicketInfos(int limit);

    List<TicketInfo> findTicketInfosOrderByDateTime(LocalDateTime dateTime, int limit);
}
