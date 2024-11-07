package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.user.response.SignInResponse;
import kahlua.KahluaProject.dto.user.response.TokenResponse;

public class AuthConverter {
    public static SignInResponse toSignInResDto(User user, TokenResponse tokenResponse) {
        return SignInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .term(user.getTerm())
                .session(String.valueOf(user.getSession()))
                .role(user.getUserType())
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(tokenResponse.getRefreshToken())
                .build();
    }
}
