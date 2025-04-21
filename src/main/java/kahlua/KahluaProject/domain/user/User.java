package kahlua.KahluaProject.domain.user;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import kahlua.KahluaProject.dto.user.request.UserInfoRequest;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

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

    @Column(columnDefinition = "varchar(255)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column(columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private Session session;

    @Column(columnDefinition = "bigint")
    private Long term;

    @Column(nullable = false, columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Builder
    public User(String email, Credential credential, String name, UserType userType, Session session, Long term, LoginType loginType) {
        this.email = email;
        this.credential = credential;
        this.name = name;
        this.userType = userType;
        this.session = session;
        this.term = term;
        this.loginType = loginType;
    }

    public void updateUserInfo(UserInfoRequest userInfoRequest) {
        this.name = userInfoRequest.name();
        this.term = userInfoRequest.term();
        this.session = Session.valueOf(userInfoRequest.session());
        this.userType = UserType.PENDING;
    }

    public void updateUserType(UserType newType) {
        this.userType = newType;
    }
}
