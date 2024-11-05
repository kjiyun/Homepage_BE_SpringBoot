package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.request.PostImageCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;
import kahlua.KahluaProject.dto.post.response.PostImageCreateResponse;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostConverter {

    public static Post toPost(PostCreateRequest postCreateRequest) {
        return Post.builder()
                .title(postCreateRequest.getTitle())
                .content(postCreateRequest.getContent())
                .build();
    }

    public static PostCreateResponse toPostCreateResponse(Post post, User user, List<PostImageCreateResponse> imageUrls) {
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

    public static List<PostImage> toPostImage(List<PostImageCreateRequest> postImageCreateRequests, Post post) {
        return Optional.ofNullable(postImageCreateRequests)
                .orElse(Collections.emptyList())
                .stream()
                .map((PostImageCreateRequest imageRequest) -> PostImage.builder()
                        .url(imageRequest.getUrl())
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<PostImageCreateResponse> toPostImageCreateResponse(List<PostImage> postImage) {
        List<PostImageCreateResponse> imageUrlResponses = postImage.stream()
                .map(postImage1 -> PostImageCreateResponse.builder()
                        .id(postImage1.getId())
                        .url(postImage1.getUrl())
                        .build())
                .collect(Collectors.toList());

        return imageUrlResponses;
    }
}
