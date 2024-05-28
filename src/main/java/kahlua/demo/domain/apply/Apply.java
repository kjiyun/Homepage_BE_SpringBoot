package kahlua.demo.domain.apply;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import kahlua.demo.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Apply extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Long id;

    private String name;

    private String phone_num;

    private String birth_date;

    /*@Enumerated(EnumType.STRING)
    private Gender gender;*/
    private String gender;

    private String address;

    /*@Enumerated(EnumType.STRING)
    private Major major;*/
    private String major;

    /*@Enumerated(EnumType.STRING)
    private Preference first_preference;

    @Enumerated(EnumType.STRING)
    private Preference second_preference;*/
    private String first_preference;
    private String second_preference;

    private String experience_and_reason;

    private String play_instrument;

    private String motive;

    private String finish_time;

    // @ColumnDefault("false")
    private Boolean meeting;

    private String readiness;
}
