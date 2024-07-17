package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.response.SignInResponse;
import kahlua.KahluaProject.dto.response.TokenResponse;

public class AuthConverter {
    public static SignInResponse toSignInResDto(User user, TokenResponse tokenResponse) {
        return SignInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getUserType())
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .build();
    }
}
