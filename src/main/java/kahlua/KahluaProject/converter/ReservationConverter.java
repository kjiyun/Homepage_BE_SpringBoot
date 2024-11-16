package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.reservation.Reservation;
import kahlua.KahluaProject.domain.reservation.ReservationStatus;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.reservation.request.ReservationRequest;
import kahlua.KahluaProject.dto.reservation.response.ReservationResponse;

import java.time.LocalDate;

public class ReservationConverter {


    public static Reservation toReservation(ReservationRequest reservationRequest, User user, LocalDate reservationDate, ReservationStatus status) {

        return Reservation.builder()
                .user(user)
                .type(reservationRequest.type())
                .clubRoomUsername(reservationRequest.clubroomUsername())
                .reservationDate(reservationDate)
                .startTime(reservationRequest.startTime())
                .endTime(reservationRequest.endTime())
                .status(status)
                .build();
    }
    public static ReservationResponse toReservationResponse(Reservation reservation, String email) {

        return ReservationResponse.builder()
                .reservationId(reservation.getId())
                .email(email)
                .type(reservation.getType())
                .clubroomUsername(reservation.getClubRoomUsername())
                .reservationDate(reservation.getReservationDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .status(reservation.getStatus())
                .build();


    }
}
