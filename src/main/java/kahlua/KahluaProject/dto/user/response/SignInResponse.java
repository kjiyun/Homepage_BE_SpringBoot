package kahlua.KahluaProject.dto.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import kahlua.KahluaProject.domain.user.UserType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignInResponse {

    @Schema(description = "아이디")
    private Long id;

    @Schema(description = "이메일(아이디)")
    private String email;

    @Schema(description = "권한")
    private UserType role;

    @Schema(description = "Access Token")
    private String accessToken;

    @Schema(description = "Refresh Token")
    private String refreshToken;
}
