package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.reservation.Reservation;
import kahlua.KahluaProject.domain.reservation.ReservationStatus;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.reservation.request.ReservationProceedRequest;
import kahlua.KahluaProject.dto.reservation.request.ReservationRequest;
import kahlua.KahluaProject.dto.reservation.response.ReservationListResponse;
import kahlua.KahluaProject.dto.reservation.response.ReservationResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.reservation.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static kahlua.KahluaProject.converter.ReservationConverter.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;

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

    @Transactional
    public ReservationResponse save(ReservationRequest reservationRequest, String reservationDate, Map<String, Object> header) {

        String email = getValueFromHeader(header, "email");
        User user = userService.getUserByEmail(email);

        Reservation reservation = toReservation(reservationRequest, user, toLocalDate(reservationDate), ReservationStatus.RESERVED);
        Reservation savedReservation = reservationRepository.save(reservation);

        return toReservationResponse(savedReservation, email);
    }

    @Transactional
    public ReservationListResponse getByDate(LocalDate date) {

        List<Reservation> reservationList = reservationRepository.findByDate(date);

        List<ReservationResponse> reservationResponseList = reservationList.stream()
                .map(reservation -> toReservationResponse(reservation, reservation.getUser().getEmail()))
                .collect(Collectors.toList());

        return new ReservationListResponse(reservationResponseList);
    }

    public ReservationListResponse getByUser(User user) {

        List<Reservation> reservationList = reservationRepository.findByUser(user);

        List<ReservationResponse> reservationResponseList = reservationList.stream()
                .map(reservation -> toReservationResponse(reservation, user.getEmail()))
                .collect(Collectors.toList());

        return new ReservationListResponse(reservationResponseList);
    }

    @Transactional
    public void delete(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new GeneralException(ErrorStatus.RESERVATION_NOT_FOUND));

        reservationRepository.delete(reservation);
    }

    // String to LocalDateTime
    private LocalDate toLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private String getValueFromHeader(Map<String, Object> header, String key) {
        return (String)header.get(key);
    }

}
