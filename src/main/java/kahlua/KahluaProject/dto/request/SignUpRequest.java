package kahlua.KahluaProject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import lombok.Builder;
@Builder
@Schema(title = "회원가입 요청 DTO")
public record SignUpRequest(
        @NotEmpty
        @Schema(description = "사용자 이메일", example = "kahlua123@kahlua.com")
        String email,

        @NotEmpty
        @Schema(description = "사용자 비밀번호", example = "kahlua123")
        String password,

        @NotEmpty
        @Schema(description = "사용자 비밀번호 확인", example = "kahlua123")
        String passwordCheck,

        String userType
) {

    public User from(Credential credential) {
        return User.builder()
                .credential(credential)
                .email(this.email)
                .userType(UserType.valueOf(this.userType))
                .build();
    }
}