package kahlua.KahluaProject.dto.reservation.request;

import lombok.Builder;

@Builder
public record SubRequest(
        String type,
        String email,
        String content
) {
}
