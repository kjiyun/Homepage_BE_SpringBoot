package kahlua.KahluaProject.controller;

import kahlua.KahluaProject.security.kakao.KakaoService;
import kahlua.KahluaProject.security.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {
    private final KakaoService kakaoService;

    @GetMapping("/callback")
    public ResponseEntity<KakaoToken> callback(@RequestParam("code") String code){
        KakaoToken kakaoToken = kakaoService.getAccessTokenFromKakao(code);
        return new ResponseEntity<>(kakaoToken, HttpStatus.OK);
    }
}
