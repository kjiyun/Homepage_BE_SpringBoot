package kahlua.KahluaProject.repository.post;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostLikes;
import kahlua.KahluaProject.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {

//        boolean existsByPostAndUser(Post);
        Optional<PostLikes> findByPostAndUser(Post post, User user);
}
