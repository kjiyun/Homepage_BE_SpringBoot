package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.AuthConverter;
import kahlua.KahluaProject.converter.UserConverter;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.user.request.SignInRequest;
import kahlua.KahluaProject.dto.user.request.SignUpRequest;
import kahlua.KahluaProject.dto.user.response.SignInResponse;
import kahlua.KahluaProject.dto.user.response.TokenResponse;
import kahlua.KahluaProject.dto.user.response.UserResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.redis.RedisClient;
import kahlua.KahluaProject.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final CredentialService credentialService;
    private final JwtProvider jwtProvider;
    private final RedisClient redisClient;

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {
        Credential credential = credentialService.createCredential(signUpRequest);
        User user = userService.createUser(credential, signUpRequest);

        return UserConverter.toUserResDto(user);
    }

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userService.getUserByEmail(signInRequest.getEmail());
        credentialService.checkPassword(user, signInRequest.getPassword());

        TokenResponse tokenResponse = jwtProvider.createToken(user);
        redisClient.setValue(user.getEmail(), tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 24 * 7L);

        return AuthConverter.toSignInResDto(user, tokenResponse);
    }

    @Transactional
    public void signOut(String refreshToken, String accessToken) {
        jwtProvider.invalidateTokens(refreshToken, accessToken);
    }

    public TokenResponse recreate(String token, User user) {
        if (user == null) {
            throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
        }

        String refreshToken = token.substring(7);
        boolean isValid = jwtProvider.validateToken(refreshToken);

        if (!isValid) {
            throw new GeneralException(ErrorStatus.TOKEN_INVALID);
        }

        String email = jwtProvider.getEmail(refreshToken);
        String redisRefreshToken = redisClient.getValue(email);

        if (StringUtils.isEmpty(refreshToken) || StringUtils.isEmpty(redisRefreshToken) || !redisRefreshToken.equals(refreshToken)) {
            throw new GeneralException(ErrorStatus.TOKEN_INVALID);
        }

        return jwtProvider.recreate(user, refreshToken);
    }
}
