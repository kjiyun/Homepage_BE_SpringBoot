package kahlua.KahluaProject.apipayload.code.status;

import kahlua.KahluaProject.apipayload.code.BaseCode;
import kahlua.KahluaProject.apipayload.code.ReasonDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import javax.management.loading.MLetContent;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseCode {

    // 에러 응답
    INTERNER_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "서버 에러"),

    // 로그인 실패
    LOGIN_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "LOGIN FAIL", "아이디 또는 비밀번호를 확인하세요"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER NOT FOUND", "사용자를 찾을 수 없습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", ""),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "권한이 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.NOT_FOUND, "PASSWORD_NOT_MATCH", "비밀번호가 틀렸습니다."),
    PASSWORD_INVALID(HttpStatus.NOT_FOUND, "PASSWORD_INVALID", "유효하지 않은 password"),
    TOKEN_INVALID(HttpStatus.NOT_FOUND, "TOKEN_INVALID", "토큰 유효하지 않음"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "s"),


    // 세션 에러
    SESSION_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "유효하지 않은 세션입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDto getReason() {
        return ReasonDto.builder()
                .isSuccess(false)
                .httpStatus(httpStatus)
                .code(code)
                .message(message)
                .build();
    }

}