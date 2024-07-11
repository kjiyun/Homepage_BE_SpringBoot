package kahlua.KahluaProject.domain.ticket;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")

    private Long id;

    private String buyer; // 구매자 이름

    private String phone_num; // 구매자 번호

    @OneToMany(mappedBy = "ticket")
    private List<Participants> members;  // 참석 인원

    private String reservation_id; // 예약 번호

    /*@Enumerated(EnumType.STRING)
    private Type type;*/
    private String type;  // 티켓 유형 : 신입생 혹은 일반
}
