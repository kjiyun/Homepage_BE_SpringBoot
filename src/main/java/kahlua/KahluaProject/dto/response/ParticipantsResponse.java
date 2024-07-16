package kahlua.KahluaProject.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ParticipantsResponse {

    private Long id;
    private String name;
    private String phone_num;
}
