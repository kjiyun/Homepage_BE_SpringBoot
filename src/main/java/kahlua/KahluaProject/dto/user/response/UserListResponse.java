package kahlua.KahluaProject.dto.user.response;

import kahlua.KahluaProject.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
public class UserListResponse {
    private List<UserListOneResponse> content;
    private long pendingCount;
    private long approvedCount;
    private PageInfo pageInfo;

    @Getter
    @Builder
    public static class PageInfo {
        private int nextPage;
        private int lastPage;
    }

    public static UserListResponse of(Page<User> users, long pendingCount, long approvedCount) {

        PageInfo pageInfo = PageInfo.builder()
                .nextPage(users.hasNext() ? users.getNumber() + 1 : -1)
                .lastPage(Math.max(users.getTotalPages() - 1, 0))
                .build();

        return UserListResponse.builder()
                .content(users.getContent()
                        .stream()
                        .map(UserListOneResponse::from)
                        .toList())
                .pendingCount(pendingCount)
                .approvedCount(approvedCount)
                .pageInfo(pageInfo)
                .build();
    }

}
