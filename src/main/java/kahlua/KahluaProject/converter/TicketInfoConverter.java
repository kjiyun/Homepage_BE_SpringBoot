package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import kahlua.KahluaProject.dto.ticketInfo.request.TicketInfoRequest;
import kahlua.KahluaProject.dto.ticketInfo.response.PerformanceRes;
import kahlua.KahluaProject.dto.ticketInfo.response.TicketInfoResponse;
import kahlua.KahluaProject.vo.TicketInfoData;

public class TicketInfoConverter {

    public static PerformanceRes.performanceDto toPerformanceDto(TicketInfo info, PerformanceStatus status){
        return PerformanceRes.performanceDto.builder()
                .ticketInfoId(info.getId())
                .title(info.getTicketInfoData().title())
                .content(info.getTicketInfoData().content())
                .status(status)
                .posterUrl(info.getPosterImageUrl())
                .build();
    }

    public static TicketInfo toCreateTicketInfo(TicketInfoRequest request) {
        TicketInfoData ticketInfoData = new TicketInfoData(
                request.title(),
                request.content(),
                request.venue(),
                request.address(),
                request.dateTime(),
                request.freshmanPrice(),
                request.freshmanMaxPurchase(),
                request.generalPrice(),
                request.generalMaxPurchase(),
                request.bookingStartDate(),
                request.bookingEndDate()
        );
        return TicketInfo.builder()
                .posterImageUrl(request.posterImageUrl())
                .ticketInfoData(ticketInfoData)
                .build();
    }

    public static TicketInfoResponse toCreateTicketInfoResponse(TicketInfo ticketInfo) {
        TicketInfoData data = ticketInfo.getTicketInfoData();
        return TicketInfoResponse.builder()
                .id(ticketInfo.getId())
                .posterImageUrl(ticketInfo.getPosterImageUrl())
                .title(data.title())
                .content(data.content())
                .venue(data.venue())
                .address(data.address())
                .dateTime(data.dateTime())
                .freshmanPrice(data.freshmanPrice())
                .freshmanMaxPurchase(data.freshmanMaxPurchase())
                .generalPrice(data.generalPrice())
                .generalMaxPurchase(data.generalMaxPurchase())
                .bookingStartDate(data.bookingStartDate())
                .bookingEndDate(data.bookingEndDate())
                .build();
    }
}
