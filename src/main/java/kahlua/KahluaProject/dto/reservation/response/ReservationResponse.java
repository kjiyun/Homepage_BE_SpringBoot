package kahlua.KahluaProject.dto.reservation.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import kahlua.KahluaProject.domain.reservation.ReservationStatus;
import kahlua.KahluaProject.domain.reservation.ReservationType;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record ReservationResponse(
        Long reservationId,
        String email,
        ReservationType type,
        String clubroomUsername,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        LocalDate reservationDate,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime endTime,

        ReservationStatus status
) {
}
