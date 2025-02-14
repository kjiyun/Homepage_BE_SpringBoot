package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.PostConverter;
import kahlua.KahluaProject.converter.TicketConverter;
import kahlua.KahluaProject.converter.TicketInfoConverter;
import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import kahlua.KahluaProject.domain.ticket.Ticket;
import kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus;
import kahlua.KahluaProject.domain.ticketInfo.TicketInfo;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;
import kahlua.KahluaProject.dto.post.response.PostImageCreateResponse;
import kahlua.KahluaProject.dto.ticketInfo.request.TicketInfoRequest;
import kahlua.KahluaProject.dto.ticketInfo.response.PerformanceRes;
import kahlua.KahluaProject.dto.ticketInfo.response.TicketInfoResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ticket.TicketInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kahlua.KahluaProject.domain.post.PostType.KAHLUA_TIME;
import static kahlua.KahluaProject.domain.post.PostType.NOTICE;
import static kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus.CLOSED;
import static kahlua.KahluaProject.domain.ticketInfo.PerformanceStatus.OPEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformanceService {
    private final TicketInfoRepository ticketInfoRepository;

    public PerformanceRes.performanceListDto getPerformances(Long cursor, int limit){
        Pageable pageable = PageRequest.of(0, limit+1);

        List<TicketInfo> ticketInfos;
        if (cursor == null) {
            ticketInfos = ticketInfoRepository.findAllByOrderByIdDesc(pageable);
        } else {
            ticketInfos = ticketInfoRepository.findAllByIdLessThanOrderByIdDesc(cursor, pageable);
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
        if (LocalDateTime.now().isAfter(ticketInfo.getTicketInfoData().bookingEndDate()) && LocalDateTime.now().isBefore(ticketInfo.getTicketInfoData().bookingStartDate())) {
            return CLOSED;
        } else{return OPEN;}
    }

    public PerformanceRes.performanceInfoDto getPerformanceInfo(Long ticketInfoId){
        TicketInfo ticketInfo = ticketInfoRepository.findById(ticketInfoId)
                .orElseThrow(()->new GeneralException(ErrorStatus.TICKETINFO_NOT_FOUND));

        return PerformanceRes.performanceInfoDto.builder()
                .ticketInfoResponse(TicketConverter.toTicketInfoResponse(ticketInfo))
                .status(checkStatus(ticketInfo))
                .build();
    }

    public TicketInfoResponse createPerformance(TicketInfoRequest request, User user) {
        if(user.getUserType() != UserType.ADMIN) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
        TicketInfo ticketInfo = TicketInfoConverter.toCreateTicketInfo(request);
        ticketInfoRepository.save(ticketInfo);

        return TicketInfoConverter.toCreateTicketInfoResponse(ticketInfo);
    }
}
