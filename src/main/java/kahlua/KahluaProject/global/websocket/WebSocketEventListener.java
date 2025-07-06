package kahlua.KahluaProject.global.websocket;

import kahlua.KahluaProject.dto.reservation.request.SubRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    // WebSocket 연결, 구독, 해제 event 로깅
    // + 구독자들에게 구독, 해제 메세지 전송 여부는 논의 필요

    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

    private final SimpMessageSendingOperations messagingTemplate;

    // 연결 요청
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("Received a new web socket connection");
    }

    // 구독 요청(입장)
    @EventListener
    public void handleWebSocketSubscribeListener(SessionSubscribeEvent event) {
        logger.info("Received a new web socket subscribe");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String email = (String)getValue(accessor, "email");
        String destination = accessor.getDestination();
        String reservationDate = destination.substring(destination.lastIndexOf('/') + 1);

        logger.info("User: {} Subscribe ReservationDate : {}", email, reservationDate);

        SubRequest subRequest = SubRequest.builder()
                .type("JOIN")
                .email(email)
                .content(email + " 님이 입장했습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/public/" + reservationDate, subRequest);

    }

    // 연결 해제
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String email = (String)getValue(accessor, "email");
        String reservationDate = (String)getValue(accessor, "reservationDate");

        logger.info("User: {} Disconnected ReservationDate : {}", email, reservationDate);

        SubRequest subRequest = SubRequest.builder()
                .type("LEAVE")
                .email(email)
                .content(email + " 님이 떠났습니다.")
                .build();

        messagingTemplate.convertAndSend("/topic/public/" + reservationDate, subRequest);
    }

    private Object getValue(StompHeaderAccessor accessor, String key) {
        Map<String, Object> sessionAttributes = getSessionAttributes(accessor);
        Object value = sessionAttributes.get(key);

        if (Objects.isNull(value)) {
            throw new WebSocketException(key + " 에 해당하는 값이 없습니다.");
        }

        return value;
    }

    private Map<String, Object> getSessionAttributes(StompHeaderAccessor accessor) {
        Map<String, Object> sessionAttributes = accessor.getSessionAttributes();

        if (Objects.isNull(sessionAttributes)) {
            throw new WebSocketException("SessionAttributes가 null입니다.");
        }
        return sessionAttributes;
    }
}