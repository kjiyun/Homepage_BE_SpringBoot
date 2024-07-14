package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.request.SignInRequest;
import kahlua.KahluaProject.dto.request.SignUpRequest;
import kahlua.KahluaProject.facade.AuthFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Tag(name = "인증", description = "사용자 회원가입/로그인 등 인증과 관련된 API")
@Slf4j
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "사용자의 정보 입력 후 회원가입에 따른 성공/실패 여부 반환")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity
                .created(URI.create("v1/auth/sign-in"))
                .body(ApiResponse.onSuccess(authFacade.signUp(signUpRequest)));
    }

    @PostMapping("/sign-in")
    @Operation(summary = "로그인", description = "사용자의 정보 입력 후 회원가입에 따른 성공/실패 여부 반환")
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInRequest signInRequest) {
        return ResponseEntity
                .ok()
                .body(ApiResponse.onSuccess(authFacade.signIn(signInRequest)));
    }
}
