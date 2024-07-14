package kahlua.KahluaProject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kahlua.KahluaProject.domain.user.User;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record SignInResponse(
        Long id,

        String email,

        String role,

        String token
) {
    public static SignInResponse of(User user, String token) {
        return SignInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getUserType().toString())
                .token(token)
                .build();
    }
}