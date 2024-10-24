package kahlua.KahluaProject.security.kakao;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.util.logging.Slf4j;
import io.netty.handler.codec.http.HttpHeaderValues;
import kahlua.KahluaProject.security.kakao.dto.KakaoProfile;
import kahlua.KahluaProject.security.kakao.dto.KakaoToken;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoService {

    private static final Logger log = LoggerFactory.getLogger(KakaoService.class);
    // Kakao API 호출에 필요한 클라이언트 ID
    @Value("${kakao.client_id}")
    private String clientId;

    // Kakao 토큰을 요청하는 URL 호스트 주소
    private final String KAUTH_TOKEN_URL_HOST = "https://kauth.kakao.com";

    // Kakao 사용자 정보를 요청하는 URL 호스트 주소
    private final String KAUTH_USER_URL_HOST = "https://kapi.kakao.com";


    // Kakao 인증 서버에서 받은 code를 이용해 액세스 토큰을 가져오는 메서드
    public KakaoToken getAccessTokenFromKakao(String code) {
        // WebClient를 사용해 Kakao API에 POST 요청을 보냄
        return WebClient.create(KAUTH_TOKEN_URL_HOST).post()
                // 요청할 URI를 설정
                .uri(uriBuilder -> uriBuilder
                        .path("/oauth/token") // 토큰을 요청하는 경로
                        .queryParam("grant_type", "authorization_code") // OAuth 인증 방식 중 authorization_code 사용
                        .queryParam("client_id", clientId) // 클라이언트 ID를 추가
                        .queryParam("code", code) // 사용자에게서 받은 인증 코드를 추가
                        .build())
                // 헤더에 Content-Type 설정 (x-www-form-urlencoded 형식으로 전송)
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                // 응답을 받아오는 과정
                .retrieve()
                // 에러 발생 시 로그 남기기
                .onStatus(HttpStatusCode::isError, clientResponse -> {
                    log.error("Failed to get access token: {}", clientResponse.statusCode());
                    return Mono.error(new RuntimeException("Failed to get access token"));
                })
                // 응답을 KakaoToken 클래스로 변환
                .bodyToMono(KakaoToken.class)
                .doOnError(e -> log.error("Error while retrieving KakaoToken: {}", e.getMessage()))
                .block(); // 블록킹 호출로 결과를 기다림
    }


    public KakaoProfile getMemberInfo(KakaoToken kakaoToken) {
        // 요청 기본 객체 생성
        WebClient webClient = WebClient.builder()
                .baseUrl(KAUTH_USER_URL_HOST)
                .defaultHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .build();
        log.info("KakaoToken: {}", kakaoToken.accessToken());

        // 요청 보내서 응답 받기
        String response = webClient.post()
                .uri("/v2/user/me") // Assuming this is the correct endpoint; adjust as necessary
                .header("Authorization", "Bearer " + kakaoToken.accessToken())
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(e -> log.error("Error while retrieving KakaoProfile: {}", e.getMessage()))
                .block();

        // 수신된 응답 Mapping
        ObjectMapper objectMapper = new ObjectMapper();
        KakaoProfile kakaoProfile;
        try {
            kakaoProfile = objectMapper.readValue(response, KakaoProfile.class);
            log.info("Received KakaoProfile: {}", kakaoProfile); // Log the profile info
        } catch (IOException e) {
            log.error("Error parsing KakaoProfile: {}", e.getMessage());
            throw new RuntimeException("Error parsing KakaoProfile");
        }

        return kakaoProfile;
    }

}
