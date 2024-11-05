package kahlua.KahluaProject.service;

import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.PostConverter;
import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostImage;
import kahlua.KahluaProject.domain.user.User;
import kahlua.KahluaProject.domain.user.UserType;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.request.PostImageCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;
import kahlua.KahluaProject.dto.post.response.PostImageCreateResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.PostImageRepository;
import kahlua.KahluaProject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest postCreateRequest, User user) {

        // 공지사항인 경우 admin인지 확인
        if(user.getUserType() != UserType.ADMIN){
            throw new GeneralException(ErrorStatus.UNAUTHORIZED);
        }

        Post post = PostConverter.toPost(postCreateRequest);
        postRepository.save(post);

        List<PostImage> imageUrls = PostConverter.toPostImage(postCreateRequest.getImageUrls(), post);
        if(imageUrls.size() > 10) throw new GeneralException(ErrorStatus.IMAGE_NOT_UPLOAD);

        postImageRepository.saveAll(imageUrls);
        List<PostImageCreateResponse> imageUrlResponses = PostConverter.toPostImageCreateResponse(imageUrls);

        PostCreateResponse postCreateResponse = PostConverter.toPostCreateResponse(post, user, imageUrlResponses);

        return postCreateResponse;
    }
}
