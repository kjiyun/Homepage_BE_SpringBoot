package kahlua.KahluaProject.dto.reservation.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import kahlua.KahluaProject.domain.reservation.ReservationType;

import java.time.LocalTime;

public record ReservationRequest(
        ReservationType type,
        String clubroomUsername,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime startTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
        LocalTime endTime
) {
}
