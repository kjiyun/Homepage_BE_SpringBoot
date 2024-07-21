package kahlua.KahluaProject.controller;

import kahlua.KahluaProject.apipayload.ApiResponse;
import kahlua.KahluaProject.dto.apply.request.ApplyCreateRequest;
import kahlua.KahluaProject.dto.apply.response.ApplyCreateResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyGetResponse;
import kahlua.KahluaProject.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/apply")
public class ApplyController {

    private final ApplyService applyService;

    @PostMapping
    public ApiResponse<ApplyCreateResponse> createApplyFrom(@RequestBody ApplyCreateRequest applyCreateRequest) {
        ApplyCreateResponse applyCreateResponse = applyService.createApply(applyCreateRequest);
        return ApiResponse.onSuccess(applyCreateResponse);
    }

    @GetMapping("/{applyId}")
    public ApiResponse<ApplyGetResponse> viewApplyForm(@PathVariable Long applyId) {
        ApplyGetResponse applyGetResponse = applyService.getApply(applyId);
        return ApiResponse.onSuccess(applyGetResponse);
    }
}
