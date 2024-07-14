package kahlua.KahluaProject.facade;

import jakarta.servlet.http.HttpServletRequest;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.request.SignInRequest;
import kahlua.KahluaProject.dto.request.SignUpRequest;
import kahlua.KahluaProject.dto.response.SignInResponse;
import kahlua.KahluaProject.dto.response.UserResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.security.jwt.JwtProvider;
import kahlua.KahluaProject.service.CredentialService;
import kahlua.KahluaProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final CredentialService credentialService;
    private final JwtProvider jwtProvider;

    @Transactional
    public UserResponse signUp(SignUpRequest signUpRequest) {
        Credential credential = credentialService.createCredential(signUpRequest);
        User user = userService.createUser(credential, signUpRequest);

        return UserResponse.of(user);
    }

    @Transactional
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userService.getUserByEmail(signInRequest.email());
        credentialService.checkPassword(user, signInRequest.password());
        String token = jwtProvider.generateToken(user);

        return SignInResponse.of(user, token);
    }
}
