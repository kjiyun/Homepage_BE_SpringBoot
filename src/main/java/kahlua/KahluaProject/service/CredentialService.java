package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.request.SignUpRequest;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class CredentialService {

    private final PasswordEncoder passwordEncoder;
    private final CredentialRepository credentialRepository;

    public Credential createCredential(SignUpRequest signUpRequest) {
        if(!signUpRequest.password().equals(signUpRequest.passwordCheck())) {
            throw new  GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        String hashedPassword = passwordEncoder.encode(signUpRequest.password());
        Credential credential = Credential.builder()
                .hashedPassword(hashedPassword)
                .build();

        return credentialRepository.save(credential);
    }

    public void checkPassword(User user, String password) {
        Credential credential = user.getCredential();

        if(!passwordEncoder.matches(password, credential.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_INVALID);
        }
    }
}
