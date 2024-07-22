package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.user.request.SignUpRequest;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(Credential credential, SignUpRequest signUpRequest) {
        userRepository.findByEmail(signUpRequest.getEmail()).ifPresent(existingUser -> {
            throw new GeneralException(ErrorStatus.ALREADY_EXIST_USER);
        });
        User user = signUpRequest.toUser(credential);
        return userRepository.save(user);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
}
