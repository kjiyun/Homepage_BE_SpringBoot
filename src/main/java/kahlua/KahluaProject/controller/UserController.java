package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.converter.UserConverter;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.security.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "사용자", description = "사용자와 관련된 API")
@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("")
    @Operation(summary = "사용자 정보 조회", description = "사용자의 정보를 조회")
    public ResponseEntity<?> getUser(@AuthenticationPrincipal AuthDetails authDetails) {
        User user = authDetails.user();
        return ResponseEntity.ok().body(ApiResponse.onSuccess(UserConverter.toUserResDto(user)));
    }
}
