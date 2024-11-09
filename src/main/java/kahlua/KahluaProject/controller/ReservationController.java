package kahlua.KahluaProject.controller;

import kahlua.KahluaProject.dto.reservation.request.ReservationProceedRequest;
import kahlua.KahluaProject.dto.reservation.request.ReservationRequest;
import kahlua.KahluaProject.dto.reservation.response.ReservationResponse;
import kahlua.KahluaProject.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    // 예약 시간 선택한 사람에게 우선권 부여
    @MessageMapping("/reserve.proceed/{date}")
    @SendTo("/topic/public/{date}")
    public ReservationResponse proceed(@DestinationVariable String reservationDate,
                                       @Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                                       @Payload ReservationProceedRequest reservationProceedRequest) {

        return reservationService.proceed(reservationProceedRequest, reservationDate, simpSessionAttributes);
    }

    // 예약 확정 후 예약내역 DB에 저장
    @MessageMapping("/reserve.complete/{date}")
    @SendTo("/topic/public/{date}")
    public ReservationResponse complete(@DestinationVariable String reservationDate,
                                       @Header("simpSessionAttributes") Map<String, Object> simpSessionAttributes,
                                       @Payload ReservationRequest reservationRequest) {

        return reservationService.save(reservationRequest, reservationDate, simpSessionAttributes);
    }
}
