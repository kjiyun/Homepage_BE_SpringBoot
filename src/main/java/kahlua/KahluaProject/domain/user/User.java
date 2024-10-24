package kahlua.KahluaProject.domain.user;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Getter
@SQLDelete(sql = "UPDATE user SET deleted_at = NOW() where id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private Credential credential;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    private String email;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private Session session;

    @Column(nullable = false, columnDefinition = "bigint")
    private Long term;

    @Builder
    public User(Long id, String email, Credential credential, UserType userType, Session session, String name, Long term) {
        this.id = id;
        this.credential = credential;
        this.email = email;
        this.name = name;
        this.userType = userType;
        this.session = session;
        this.term = term;
    }
}
