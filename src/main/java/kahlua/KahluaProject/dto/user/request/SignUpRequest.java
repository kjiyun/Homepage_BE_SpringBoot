package kahlua.KahluaProject.dto.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(title = "회원가입 요청 DTO")
public class SignUpRequest {
    @NotEmpty
    @Schema(description = "사용자 이메일", example = "kahlua123@kahlua.com")
    private String email;

    @NotEmpty
    @Schema(description = "사용자 비밀번호", example = "kahlua123")
    private String password;

    @NotEmpty
    @Schema(description = "사용자 비밀번호 확인", example = "kahlua123")
    private String passwordCheck;

    @NotEmpty
    @Schema(description = "사용자 권한", example = "GENERAL")
    private String userType;

    public User toUser(Credential credential) {
        return User.builder()
                .credential(credential)
                .email(this.email)
                .userType(UserType.GENERAL)
                .build();
    }
}
