package kahlua.KahluaProject.domain.ticket;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participants extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="participants_id")

    private Long id;

    private String name;

    private String phone_num;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @JsonBackReference
    private Ticket ticket;
}
