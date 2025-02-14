package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import kahlua.KahluaProject.dto.ticketInfo.response.PerformanceRes;

import java.time.LocalDateTime;

import static kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus.CLOSED;
import static kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus.OPEN;

public class TicketInfoConverter {

    public static PerformanceRes.performanceDto toPerformanceDto(TicketInfo info,PerformanceStatus status){
        return PerformanceRes.performanceDto.builder()
                .ticketInfoId(info.getId())
                .title(info.getTicketInfoData().title())
                .content(info.getTicketInfoData().content())
                .status(status)
                .posterUrl(info.getPosterImageUrl())
                .build();
    }

}
