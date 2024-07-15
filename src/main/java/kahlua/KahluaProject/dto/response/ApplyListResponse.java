package kahlua.KahluaProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApplyListResponse<T> {

    private T applies;
}
