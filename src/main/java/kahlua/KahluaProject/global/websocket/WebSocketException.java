package kahlua.KahluaProject.global.websocket;

import kahlua.KahluaProject.global.apipayload.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class WebSocketException extends RuntimeException {

    // 추후 인터셉터(StompSubProtocolErrorHandler),
    // 비즈니스 로직(@MessageExceptionHandler)로 구분하여 예외처리 구현 필요

    private static final ErrorStatus ERROR_CODE = ErrorStatus.WEBSOCKET_SESSION_UNAUTHORIZED;
    private static final String MESSAGE_KEY = "exception.websocket";
    private final String message;

    public WebSocketException(String message) {
        this.message = message;
    }
}