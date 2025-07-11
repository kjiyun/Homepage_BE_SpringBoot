package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.post.Comment;
import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.post.request.CommentsCreateRequest;
import kahlua.KahluaProject.dto.post.response.CommentsCreateResponse;
import kahlua.KahluaProject.dto.post.response.CommentsItemResponse;
import kahlua.KahluaProject.dto.post.response.CommentsListResponse;

import java.util.List;
import java.util.Optional;

public class CommentConverter {

    public static Comment toComment(CommentsCreateRequest commentsCreateRequest, Post existingPost, User user, Comment parentComment) {
        return Comment.builder()
                .post(existingPost)
                .user(user)
                .content(commentsCreateRequest.getContent())
                .parentComment(parentComment)
                .build();
    }

    public static CommentsCreateResponse toCommentCreateResponse(Comment comment) {
        Long parentCommentId = Optional.ofNullable(comment.getParentComment())
                .map(Comment::getId)
                .orElse(null);

        return CommentsCreateResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .user(comment.getUser().getName())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .content(comment.getContent())
                .parentCommentId(parentCommentId)
                .created_at(comment.getCreatedAt())
                .build();
    }

    public static CommentsItemResponse toCommentItemResponse(Comment comment) {
        Long parentCommentId = Optional.ofNullable(comment.getParentComment())
                .map(Comment::getId)
                .orElse(null);

        return CommentsItemResponse.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .user(comment.getUser().getName())
                .profileImageUrl(comment.getUser().getProfileImageUrl())
                .content(comment.getContent())
                .parentCommentId(parentCommentId)
                .created_at(comment.getCreatedAt())
                .deletedAt(comment.getDeletedAt())
                .build();
    }

    public static CommentsListResponse toCommentListResponse(List<CommentsItemResponse> commentsItemResponses) {
        return CommentsListResponse.builder()
                .comments_count(commentsItemResponses.stream().count())
                .comments(commentsItemResponses)
                .build();
    }
}
