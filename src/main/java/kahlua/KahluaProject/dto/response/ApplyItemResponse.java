package kahlua.KahluaProject.dto.response;

import kahlua.KahluaProject.domain.apply.Gender;
import kahlua.KahluaProject.domain.apply.Major;
import kahlua.KahluaProject.domain.apply.Preference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ApplyItemResponse {

    private Long id;
    private String name;
    private String phone_num;
    private String birth_date;

    private Gender gender;

    private String address;

    private Major major;

    private Preference first_preference;
    private Preference second_preference;
}
