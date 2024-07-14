package kahlua.KahluaProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtTokenResponse {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
