package kahlua.KahluaProject.dto.post.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostImageGetResponse {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "image url")
    private String url;
}
