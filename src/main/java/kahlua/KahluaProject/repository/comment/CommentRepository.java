package kahlua.KahluaProject.repository.comment;

import kahlua.KahluaProject.domain.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
