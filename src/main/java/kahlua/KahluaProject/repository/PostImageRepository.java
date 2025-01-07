package kahlua.KahluaProject.repository;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> deleteAllByPost(Post post);
}
