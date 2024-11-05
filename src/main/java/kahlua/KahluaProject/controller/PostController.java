package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.post.request.PostCreateRequest;
import kahlua.KahluaProject.dto.post.response.PostCreateResponse;
import kahlua.KahluaProject.security.AuthDetails;
import kahlua.KahluaProject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "게시판", description = "게시판 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/post")
public class PostController {

    private final PostService postService;

    @PostMapping("notice/create")
    @Operation(summary = "공지사항 작성", description = "창립제, 악기 구비 등 깔루아 전체 공지 내용을 작성합니다.")
    public ApiResponse<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, @AuthenticationPrincipal AuthDetails authDetails) {
        PostCreateResponse postCreateResponse = postService.createPost(postCreateRequest, authDetails.user());
        return ApiResponse.onSuccess(postCreateResponse);
    }


}
