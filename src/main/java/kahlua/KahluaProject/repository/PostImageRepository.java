package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    void deleteAllByPost(Post post);
}
