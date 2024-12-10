package kahlua.KahluaProject.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {
    QUEST("허용되지 않은 사용자"),
    GENERAL("일반 사용자"),
    KAHLUA("깔루아"),
    ADMIN("관리자");

    private final String role;
}
