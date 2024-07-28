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
    @Operation(summary = "지원 폼 생성", description = "양식에 맞춰 지원 폼을 생성합니다")
    public ApiResponse<ApplyCreateResponse> createApplyFrom(@RequestBody ApplyCreateRequest applyCreateRequest) {
        ApplyCreateResponse applyCreateResponse = applyService.createApply(applyCreateRequest);
        return ApiResponse.onSuccess(applyCreateResponse);
    }

    @GetMapping("/{applyId}")
    @Operation(summary = "지원 폼 조회", description = "지원 폼 아이디를 통해 지우너 폼을 조회합니다")
    public ApiResponse<ApplyGetResponse> viewApplyForm(@PathVariable Long applyId) {
        ApplyGetResponse applyGetResponse = applyService.getApply(applyId);
        return ApiResponse.onSuccess(applyGetResponse);
    }
}
