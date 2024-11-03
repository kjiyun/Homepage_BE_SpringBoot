package kahlua.KahluaProject.service;

import com.fasterxml.jackson.databind.JsonNode;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.AuthConverter;
import kahlua.KahluaProject.domain.user.LoginType;
import kahlua.KahluaProject.domain.user.Session;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.user.request.UserInfoRequest;
import kahlua.KahluaProject.dto.user.response.SignInResponse;
import kahlua.KahluaProject.dto.user.response.TokenResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.redis.RedisClient;
import kahlua.KahluaProject.repository.UserRepository;
import kahlua.KahluaProject.security.jwt.JwtProvider;
import kahlua.KahluaProject.security.kakao.KakaoService;
import kahlua.KahluaProject.security.kakao.dto.KakaoProfile;
import kahlua.KahluaProject.security.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SocialLoginService {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUrl;

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final KakaoService kakaoService;
    private final RedisClient redisClient;

    public SignInResponse signInWithGoogle(String codeString, UserInfoRequest userInfoRequest) {
        codeString = URLDecoder.decode(codeString, StandardCharsets.UTF_8);

        String tokenUrl = String.format("https://oauth2.googleapis.com/token?client_id=%s&client_secret=%s&code=%s&redirect_uri=%s&grant_type=authorization_code",
                googleClientId, googleClientSecret, codeString, googleRedirectUrl);

        ResponseEntity<JsonNode> response = new RestTemplate().exchange(
                tokenUrl,
                HttpMethod.POST,
                null,
                JsonNode.class
        );

        String accessToken = Objects.requireNonNull(response.getBody()).get("access_token").asText();
        String email = getGoogleUserEmail(accessToken);

        if (email != null) {
            User user = userRepository.findByEmailAndDeletedAtIsNull(email)
                    .orElseGet(() -> createUser(email, userInfoRequest, LoginType.GOOGLE));

            return AuthConverter.toSignInResDto(user, jwtProvider.createToken(user));
        }

        throw new GeneralException(ErrorStatus.USER_NOT_FOUND);
    }

    public String getGoogleUserEmail(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<MultiValueMap<String, String>> googleTokenRequest = new HttpEntity<>(headers);
        ResponseEntity<JsonNode> response = new RestTemplate().exchange(
                "https://www.googleapis.com/oauth2/v2/userinfo",
                HttpMethod.GET,
                googleTokenRequest,
                JsonNode.class
        );

        return response.getBody().get("email").asText();
    }

    @Transactional
    public SignInResponse signInWithKakao(String code, UserInfoRequest userInfoRequest) {
        // 카카오에서 액세스 토큰을 가져옴
        KakaoToken kakaoToken = kakaoService.getAccessTokenFromKakao(code);

        // 카카오 프로필 정보를 가져옴
        KakaoProfile kakaoProfile = kakaoService.getMemberInfo(kakaoToken);

        String email = kakaoProfile.kakao_account().email();

        //bussiness logic & return : 사용자 정보가 이미 있다면 해당 사용자 정보를 반환하고, 없다면 새로운 사용자 정보를 생성하여 반환
        User user = null;
        if(userRepository.findByEmailAndDeletedAtIsNull(email).isPresent()) {
            user = userRepository.findByEmailAndDeletedAtIsNull(email).orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        } else {
            createUser(email, userInfoRequest, LoginType.KAKAO);
        }

        TokenResponse tokenResponse = jwtProvider.createToken(user);
        redisClient.setValue(user.getEmail(), tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 24 * 7L);

        return AuthConverter.toSignInResDto(user, tokenResponse);
    }

    private User createUser(String email, UserInfoRequest userInfoRequest, LoginType loginType) {
        User user = User.builder()
                .email(email)
                .userType(UserType.GENERAL)
                .name(userInfoRequest.name())
                .term(userInfoRequest.term())
                .session(Session.valueOf(userInfoRequest.session()))
                .loginType(loginType)
                .credential(null)
                .build();
        return userRepository.save(user);
    }
}

