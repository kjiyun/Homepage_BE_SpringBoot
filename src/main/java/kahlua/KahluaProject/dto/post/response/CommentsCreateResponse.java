package kahlua.KahluaProject.dto.post.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentsCreateResponse {

    @Schema(description = "댓글 번호", example = "1")
    private Long id;

    @Schema(description = "게시글 번호", example = "1")
    private Long postId;

    @Schema(description = "댓글 작성자", example = "깔루아 홍길동")
    private String user;

    @Schema(description = "댓글 내용", example = "감사합니다.")
    private String content;

    @Schema(description = "대댓글인 경우 부모 댓글 id", example = "네 수고하세요.")
    private Long parentCommentId;
}
