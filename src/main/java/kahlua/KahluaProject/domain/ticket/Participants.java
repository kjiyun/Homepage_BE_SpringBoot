package kahlua.KahluaProject.domain.ticket;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Participants {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="participants_id")

    private Long id;

    private String name;

    private String phone_num;

    @ManyToOne
    @JoinColumn
    private Ticket ticket;
}
