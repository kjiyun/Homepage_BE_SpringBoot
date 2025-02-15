package kahlua.KahluaProject.domain.ticketInfo;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import kahlua.KahluaProject.vo.TicketInfoData;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TicketInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint")
    private Long id;

    @Column(nullable = false, columnDefinition = "text")
    private String posterImageUrl;

    @Column(nullable = true, columnDefinition = "text")
    private String youtubeUrl;

    @Column(columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private TicketInfoData ticketInfoData;

    public void update(String posterImageUrl, TicketInfoData ticketInfoData) {
        this.posterImageUrl = posterImageUrl;
        this.ticketInfoData =ticketInfoData;
    }
}
