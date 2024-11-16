package kahlua.KahluaProject.repository.comment;

import kahlua.KahluaProject.domain.post.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> findPureCommentListByPost(Long postId);
}
