package kahlua.KahluaProject.domain.post;

import jakarta.persistence.*;
import kahlua.KahluaProject.domain.BaseEntity;
import kahlua.KahluaProject.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 부모 댓글
    @ManyToOne
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    private boolean isDeleted = false;

    // 자식 댓글
    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();
}
