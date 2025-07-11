package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.user.*;
import kahlua.KahluaProject.dto.user.response.UserProfileResponse;
import kahlua.KahluaProject.dto.user.response.UserResponse;

public class UserConverter {

    public static UserResponse toUserResDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .role(user.getUserType())
                .name(user.getName())
                .term(user.getTerm())
                .session(String.valueOf(user.getSession()))
                .build();
    }

    public static UserProfileResponse toUserProfileResDto(User user) {
        return UserProfileResponse.builder()
                .id(user.getId())
                .userProfileImageUrl(user.getProfileImageUrl())
                .build();
    }
}
