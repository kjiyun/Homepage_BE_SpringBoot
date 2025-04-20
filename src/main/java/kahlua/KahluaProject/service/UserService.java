package kahlua.KahluaProject.service;

import kahlua.KahluaProject.dto.user.response.UserListResponse;
import kahlua.KahluaProject.global.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.UserConverter;
import kahlua.KahluaProject.domain.user.*;
import kahlua.KahluaProject.dto.user.request.SignUpRequest;
import kahlua.KahluaProject.dto.user.request.UserInfoRequest;
import kahlua.KahluaProject.dto.user.response.UserResponse;
import kahlua.KahluaProject.global.exception.GeneralException;
import kahlua.KahluaProject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User createUser(Credential credential, SignUpRequest signUpRequest) {
        userRepository.findByEmailAndDeletedAtIsNull(signUpRequest.getEmail()).ifPresent(existingUser -> {
            throw new GeneralException(ErrorStatus.ALREADY_EXIST_USER);
        });
        User user = signUpRequest.toUser(credential);
        return userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    @Transactional
    public void withdraw(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public UserResponse createUserInfo(Long userId, UserInfoRequest userInfoRequest) {
        User user = userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        user.updateUserInfo(userInfoRequest);

        return UserConverter.toUserResDto(user);
    }

    @Transactional
    public UserListResponse getUserList(String approvalStatus, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage;

        if (approvalStatus.equals("PENDING")) {
            userPage= userRepository.findAllByUserType(UserType.PENDING,pageable);
        } else if(approvalStatus.equals("APROVED")) {
            userPage=userRepository.findAllByUserTypeIn(List.of(UserType.KAHLUA, UserType.ADMIN),pageable);
        }else if (approvalStatus.equals("ALL")) {
            userPage=userRepository.findAllByUserTypeNot(UserType.GENERAL, pageable);;
        } else {
            throw new GeneralException(ErrorStatus.INVALID_USER_TYPE);
        }

        long pendingCount  = userRepository.countByUserType(UserType.PENDING);
        long approvedCount = userRepository.countByUserTypeIn(List.of(UserType.KAHLUA, UserType.ADMIN));

        return UserListResponse.of(userPage, pendingCount, approvedCount);

    }

    @Transactional
    public UserResponse changeUserType(Long userId, UserType newType) {
        User user=userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        user.updateUserType(newType);
        userRepository.save(user);

        return UserConverter.toUserResDto(user);
    }
}
