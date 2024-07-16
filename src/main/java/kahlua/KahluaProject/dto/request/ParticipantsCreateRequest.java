package kahlua.KahluaProject.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantsCreateRequest {

    private String name;
    private String phone_num;
}
