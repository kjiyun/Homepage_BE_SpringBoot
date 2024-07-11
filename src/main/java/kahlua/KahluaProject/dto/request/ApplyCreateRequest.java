package kahlua.KahluaProject.dto.request;


import kahlua.KahluaProject.domain.apply.Gender;
import kahlua.KahluaProject.domain.apply.Major;
import kahlua.KahluaProject.domain.apply.Preference;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApplyCreateRequest {

    private String name;
    private String phone_num;
    private String birth_date;

    private Gender gender;

    private String address;

    private Major major;

    private Preference first_preference;
    private Preference second_preference;

    private String experience_and_reason;
    private String play_instrument;
    private String motive;
    private String finish_time;
    private Boolean meeting;
    private String readiness;
}
