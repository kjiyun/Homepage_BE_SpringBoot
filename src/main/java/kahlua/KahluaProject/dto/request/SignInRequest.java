package kahlua.KahluaProject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInRequest {
        @NotEmpty
        @Schema(description = "아이디", example = "kahlua123@kahlua.com")
        private String email;

        @NotEmpty
        @Schema(description = "비밀번호", example = "kahlua123")
        private String password;
}
