package kahlua.demo.controller;

import kahlua.demo.apipayload.ApiResponse;
import kahlua.demo.dto.request.ApplyCreateRequest;
import kahlua.demo.dto.response.ApplyCreateResponse;
import kahlua.demo.dto.response.ApplyGetResponse;
import kahlua.demo.service.ApplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apply")
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
