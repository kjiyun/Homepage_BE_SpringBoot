package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.TicketInfoConverter;
import kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.ticketInfo.request.TicketInfoRequest;
import kahlua.KahluaProject.dto.ticketInfo.response.PerformanceRes;
import kahlua.KahluaProject.dto.ticketInfo.response.TicketInfoResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ticket.TicketInfoRepository.TicketInfoRepository;
import kahlua.KahluaProject.vo.TicketInfoData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus.CLOSED;
import static kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus.OPEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformanceService {
    private final TicketInfoRepository ticketInfoRepository;

    public PerformanceRes.performanceListDto getPerformances(Long cursor, int limit){

        List<TicketInfo> ticketInfos;
        if (cursor == null || cursor == 0) {
            ticketInfos = ticketInfoRepository.findTicketInfos(limit+1);
        } else {
            TicketInfo cursorTicketInfo= ticketInfoRepository.findById(cursor)
                    .orElseThrow(()->new GeneralException(ErrorStatus.TICKETINFO_NOT_FOUND));
            LocalDateTime cursorDateTime=cursorTicketInfo.getTicketInfoData().dateTime();
            ticketInfos = ticketInfoRepository.findTicketInfosOrderByDateTime(cursorDateTime, limit+1);
        }

        Long nextCursor = null;
        boolean hasNext= ticketInfos.size() > limit;

        if (hasNext){
            TicketInfo lastTicketInfo= ticketInfos.get(limit-1);
            nextCursor=lastTicketInfo.getId();
            ticketInfos=ticketInfos.subList(0,limit);
        }

        List<PerformanceRes.performanceDto> ticketInfoDtos=ticketInfos.stream()
                .map(ticketInfo -> TicketInfoConverter.toPerformanceDto(ticketInfo,checkStatus(ticketInfo)))
                .collect(Collectors.toList());

        return PerformanceRes.performanceListDto.builder()
                .performances(ticketInfoDtos)
                .nextcursor(nextCursor)
                .hasNext(hasNext)
                .build();
    }

    public PerformanceStatus checkStatus(TicketInfo ticketInfo) {
        if (LocalDateTime.now().isAfter(ticketInfo.getTicketInfoData().bookingEndDate()) || LocalDateTime.now().isBefore(ticketInfo.getTicketInfoData().bookingStartDate())) {
            return CLOSED;
        } else{return OPEN;}
    }

    public PerformanceRes.performanceInfoDto getPerformanceInfo(Long ticketInfoId){
        TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId)
                .orElseThrow(()->new GeneralException(ErrorStatus.TICKETINFO_NOT_FOUND));

        return PerformanceRes.performanceInfoDto.builder()
                .ticketInfoResponse(TicketInfoConverter.toTicketInfoResponse(ticketInfo))
                .status(checkStatus(ticketInfo))
                .build();
    }

    public TicketInfoResponse createPerformance(TicketInfoRequest request, User user) {
        if(user.getUserType() != UserType.ADMIN) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        String posterImageUrl = request.posterImageUrl();
        String youtubeUrl = request.youtubeUrl();
        TicketInfoData ticketInfoData = TicketInfoConverter.toTicketInfo(request);
        TicketInfo ticketInfo = TicketInfo.create(posterImageUrl, youtubeUrl, ticketInfoData);
        ticketInfoRepository.save(ticketInfo);
        return TicketInfoConverter.toTicketInfoResponse(ticketInfo);
    }
}
