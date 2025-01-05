package kahlua.KahluaProject.dto.post.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CommentsListResponse {

    @Schema(description = "전체 댓글 수")
    private Long comments_count;

    @Schema(description = "댓글 리스트")
    private List<CommentsCreateResponse> comments;
}
