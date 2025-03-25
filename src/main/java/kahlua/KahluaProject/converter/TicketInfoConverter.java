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

    public static TicketInfoData toTicketInfo(TicketInfoRequest ticketInfoRequest) {
        return TicketInfoData.builder()
                .title(ticketInfoRequest.title())
                .content(ticketInfoRequest.content())
                .venue(ticketInfoRequest.venue())
                .address(ticketInfoRequest.address())
                .dateTime(ticketInfoRequest.dateTime())
                .freshmanPrice(ticketInfoRequest.freshmanPrice())
                .freshmanMaxPurchase(ticketInfoRequest.freshmanMaxPurchase())
                .generalPrice(ticketInfoRequest.generalPrice())
                .generalMaxPurchase(ticketInfoRequest.generalMaxPurchase())
                .bookingStartDate(ticketInfoRequest.bookingStartDate())
                .bookingEndDate(ticketInfoRequest.bookingEndDate())
                .build();
    }

    public static TicketInfoResponse toTicketInfoResponse(TicketInfo ticketInfo) {
        return TicketInfoResponse.builder()
                .id(ticketInfo.getId())
                .posterImageUrl(ticketInfo.getPosterImageUrl())
                .youtubeUrl(ticketInfo.getYoutubeUrl())
                .title(ticketInfo.getTicketInfoData().title())
                .content(ticketInfo.getTicketInfoData().content())
                .venue(ticketInfo.getTicketInfoData().venue())
                .address(ticketInfo.getTicketInfoData().address())
                .dateTime(ticketInfo.getTicketInfoData().dateTime())
                .freshmanPrice(ticketInfo.getTicketInfoData().freshmanPrice())
                .freshmanMaxPurchase(ticketInfo.getTicketInfoData().freshmanMaxPurchase())
                .generalPrice(ticketInfo.getTicketInfoData().generalPrice())
                .generalMaxPurchase(ticketInfo.getTicketInfoData().generalMaxPurchase())
                .bookingStartDate(ticketInfo.getTicketInfoData().bookingStartDate())
                .bookingEndDate(ticketInfo.getTicketInfoData().bookingEndDate())
                .build();
    }
}
