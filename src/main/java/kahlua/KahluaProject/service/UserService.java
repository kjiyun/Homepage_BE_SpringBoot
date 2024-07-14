package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.request.SignUpRequest;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static kahlua.KahluaProject.domain.user.UserType.ADMIN;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void checkAdmin(User user) {
        if(!user.getUserType().equals(ADMIN)) {
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }
    }


    public User createUser(Credential credential, SignUpRequest signUpRequest) {
        User user = signUpRequest.from(credential);
        //usertype 예외처리 필요
        return userRepository.save(user);
    }


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
}
