package kahlua.KahluaProject.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record SignInRequest(
        @NotEmpty
        @Schema(description = "아이디", example = "kahlua123@kahlua.com")
        String email,

        @NotEmpty
        @Schema(description = "비밀번호", example = "kahlua123")
        String password
) {
}
