package kahlua.KahluaProject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ParticipantsCreateRequest {

    private String name;
    private String phone_num;
}
