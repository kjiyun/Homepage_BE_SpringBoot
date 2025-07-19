package kahlua.KahluaProject.service.PostSerivce;

import kahlua.KahluaProject.domain.post.Post;
import kahlua.KahluaProject.domain.post.PostType;
import kahlua.KahluaProject.dto.post.response.PostListResponse;
import kahlua.KahluaProject.global.utils.KoreanUtils;
import kahlua.KahluaProject.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostSearchService {

    private final PostRepository postRepository;
    private final KoreanUtils koreanUtils;

    public PostListResponse searchPosts(String query, PostType postType, int page, int size) {
        Page<Post> posts;
        Pageable pageable = PageRequest.of(page, size);

        if (koreanUtils.isChosungQuery(query)) {
            // 초성 검색

            List<Post> allPosts;

            if (postType != null) {
                allPosts = postRepository.findByPostType(postType);
            } else {
                allPosts = postRepository.findAll();
            }

            List<Post> filteredPosts = allPosts.stream()
                    .filter(post -> koreanUtils.matchesChosung(post.getTitle(), query))
                    .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                    .collect(Collectors.toList());

            int start = (int) pageable.getOffset();
            int end = Math.min(start + pageable.getPageSize(), filteredPosts.size());
            List<Post> pagedPosts = filteredPosts.subList(start, end);

            posts = new PageImpl<>(pagedPosts, pageable, filteredPosts.size());
        } else {
            // 일반 검색
            if (postType != null) {
                posts = postRepository.findByTitleContainingIgnoreCaseAndPostType(query, postType, pageable);
            } else {
                posts = postRepository.findByTitleContainingIgnoreCase(query, pageable);
            }
        }

        return PostListResponse.of(posts);
    }

}
