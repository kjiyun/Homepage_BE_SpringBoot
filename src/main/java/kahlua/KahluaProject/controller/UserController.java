package kahlua.KahluaProject.controller;

import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.response.UserResponse;
import kahlua.KahluaProject.security.AuthDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("")
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
        User user = authDetails.getUser();
        return ResponseEntity.ok().body(ApiResponse.onSuccess(UserResponse.of(user)));
    }
}
