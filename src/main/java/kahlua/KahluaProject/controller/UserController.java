package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.converter.UserConverter;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.user.request.UserInfoRequest;
import kahlua.KahluaProject.dto.user.response.UserResponse;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Tag(name = "사용자", description = "사용자와 관련된 API")
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("")
    @Operation(summary = "사용자 정보 입력", description = "사용자의 정보를 입력")
    public ApiResponse<UserResponse> createUserInfo(@AuthenticationPrincipal AuthDetails authDetails,
                                                    @RequestBody(required = false) UserInfoRequest userInfoRequest) {
        return ApiResponse.onSuccess(userService.createUserInfo(authDetails.user().getId(), userInfoRequest));
    }

    @GetMapping("")
    @Operation(summary = "사용자 정보 조회", description = "사용자의 정보를 조회")
    public ResponseEntity<?> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AuthDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        AuthDetails authDetails = (AuthDetails) principal;
        User user = authDetails.user();
        return ResponseEntity.ok().body(ApiResponse.onSuccess(UserConverter.toUserResDto(user)));
    }
}
