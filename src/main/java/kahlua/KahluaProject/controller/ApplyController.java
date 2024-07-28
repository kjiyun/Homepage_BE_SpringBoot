package kahlua.KahluaProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.apply.request.ApplyCreateRequest;
import kahlua.KahluaProject.dto.apply.response.ApplyCreateResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyGetResponse;
import kahlua.KahluaProject.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "지원하기", description = "지원하기 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/apply")
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping
    @Operation(summary = "지원서 생성", description = "양식에 맞춰 지원서을 생성합니다")
    public ApiResponse<ApplyCreateResponse> createApplyFrom(@RequestBody ApplyCreateRequest applyCreateRequest) {
        return ApiResponse.onSuccess(applyService.createApply(applyCreateRequest));
    }

    @GetMapping("/{applyId}")
    @Operation(summary = "지원서 조회", description = "지원 폼 아이디를 통해 지원서를 조회합니다")
    public ApiResponse<ApplyGetResponse> getApplyForm(@PathVariable Long applyId) {
        return ApiResponse.onSuccess(applyService.getApply(applyId));
    }
}
