package kahlua.KahluaProject.domain.kahluaInfo;

import jakarta.persistence.*;
import lombok.*;
import java.time.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class LeaderInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leader_info_id")
    private Long id;

    @Column(name = "leader_name", nullable = false)
    private String leaderName; //기장 이름

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber; //기장 전화번호

    @Column(nullable = false)
    private String email; //기장 이메일

    public void update(String leaderName,
                       String phoneNumber,
                       String email) {
        this.leaderName = leaderName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
