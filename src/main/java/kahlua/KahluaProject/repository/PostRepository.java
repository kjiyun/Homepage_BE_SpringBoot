package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
