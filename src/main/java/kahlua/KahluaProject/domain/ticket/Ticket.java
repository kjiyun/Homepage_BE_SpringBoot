package kahlua.KahluaProject.domain.ticket;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.*;

import java.util.ArrayList;
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

    @Column(nullable = false)
    private String buyer; // 구매자 이름

    @Column(nullable = false)
    private String phone_num; // 구매자 번호

    @Setter
    private String reservation_id; // 예약 번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;  // 티켓 유형 : 신입생 혹은 일반

    private String major; // 학과 (신입생)

    private String student_id; // 학번 (신입생)

    @Enumerated(EnumType.STRING)
    private Meeting meeting; // 가능한 뒷풀이 참석 날짜 (신입생)

    @Builder.Default
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Participants> members = new ArrayList<>();  // 참석 인원 (일반)

    @Enumerated(EnumType.STRING)
    private Status status;  // 결제 상태 (대기, 종료, 취소)

    @PrePersist
    public void onCreate() {
        if (status == null) {
            status = Status.WAIT;  // default 값 설정
        }
    }

    public void addMembers(Participants participants) {
        members.add(participants);
        participants.setTicket(this);
    }
}
