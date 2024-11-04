package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;

import java.util.List;

public class PostConverter {

    public static Post toPost(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();
    }

    public static PostCreateResponse toPostCreateResponse(Post post, User user, List<PostImage> imageUrls) {
        return PostCreateResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .writer(user.getEmail())
                .likes(post.getLikes())
                .imageUrls(imageUrls)
                .created_at(post.getCreatedAt())
                .created_at(post.getUpdatedAt())
                .build();
    }
}
