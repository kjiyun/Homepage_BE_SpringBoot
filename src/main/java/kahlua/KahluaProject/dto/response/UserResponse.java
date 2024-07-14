package kahlua.KahluaProject.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserResponse(
        Long id,

        String email,

        UserType role
) {
    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getUserType())
                .build();
    }
}
