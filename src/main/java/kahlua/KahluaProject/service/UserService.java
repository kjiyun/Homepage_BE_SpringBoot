package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.domain.user.Credential;
import kahlua.KahluaProject.domain.user.Session;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.user.request.SignUpRequest;
import kahlua.KahluaProject.dto.user.request.UserInfoRequest;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.UserRepository;
import kahlua.KahluaProject.security.kakao.dto.KakaoProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User SignInWithKakao(KakaoProfile kakaoProfile, UserInfoRequest userInfoRequest) {
        // validation: 이메일 유효성 확인
        String email = kakaoProfile.kakao_account().email();

        //bussiness logic & return : 사용자 정보가 이미 있다면 해당 사용자 정보를 반환하고, 없다면 새로운 사용자 정보를 생성하여 반환
        if(userRepository.findByEmail(email).isPresent()) {
            return userRepository.findByEmail(email).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        } else {
            User user = User.builder()
                .email(email)
                .userType(UserType.GENERAL)
                .name(userInfoRequest.name())
                .term(userInfoRequest.term())
                .session(Session.valueOf(userInfoRequest.session()))
                .credential(null)
                .build();
            return userRepository.save(user);
        }
    }

    @Transactional
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
