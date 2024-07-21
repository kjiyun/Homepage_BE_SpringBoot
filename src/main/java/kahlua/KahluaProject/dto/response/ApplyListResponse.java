package kahlua.KahluaProject.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(title = "지원자 리스트 응답 DTO")
public class ApplyListResponse<T> {

    @Schema(description = "지원자 리스트")
    private T applies;
}
