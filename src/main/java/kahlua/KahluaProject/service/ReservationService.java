package kahlua.KahluaProject.service;

import kahlua.KahluaProject.converter.ReservationConverter;
import kahlua.KahluaProject.domain.reservation.Reservation;
import kahlua.KahluaProject.domain.reservation.ReservationStatus;
import kahlua.KahluaProject.dto.reservation.request.ReservationProceedRequest;
import kahlua.KahluaProject.dto.reservation.request.ReservationRequest;
import kahlua.KahluaProject.dto.reservation.response.ReservationResponse;
import kahlua.KahluaProject.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationResponse proceed(ReservationProceedRequest reservationProceedRequest, String reservationDate, Map<String, Object> header) {

        String email = getValueFromHeader(header, "email");

        return ReservationResponse.builder()
                .email(email)
                .reservationDate(toLocalDate(reservationDate))
                .startTime(reservationProceedRequest.startTime())
                .endTime(reservationProceedRequest.endTime())
                .status(ReservationStatus.PROCEEDING)
                .build();
    }

    // String to LocalDateTime
    private LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private ReservationResponse toReservationResponse(Reservation reservation, Map<String, Object> header) {
        String email = getValueFromHeader(header, "email");

        return ReservationConverter.toReservationResponse(reservation, email);
    }

    private String getValueFromHeader(Map<String, Object> header, String key) {
        return (String)header.get(key);
    }

}
