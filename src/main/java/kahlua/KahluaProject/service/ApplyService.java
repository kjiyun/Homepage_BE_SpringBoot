package kahlua.KahluaProject.service;

import jakarta.transaction.Transactional;
import kahlua.KahluaProject.apipayload.code.status.ErrorStatus;
import kahlua.KahluaProject.converter.ApplyConverter;
import kahlua.KahluaProject.domain.apply.Apply;
import kahlua.KahluaProject.dto.request.ApplyCreateRequest;
import kahlua.KahluaProject.dto.response.ApplyCreateResponse;
import kahlua.KahluaProject.dto.response.ApplyGetResponse;
import kahlua.KahluaProject.dto.response.ApplyItemResponse;
import kahlua.KahluaProject.dto.response.ApplyListResponse;
import kahlua.KahluaProject.exception.GeneralException;
import kahlua.KahluaProject.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyRepository applyRepository;

    @Transactional
    public ApplyCreateResponse createApply(ApplyCreateRequest applyCreateRequest) {

        Apply apply = ApplyConverter.toApply(applyCreateRequest);
        applyRepository.save(apply);

        ApplyCreateResponse applyCreateResponse = ApplyConverter.toApplyCreateResponse(apply);
        return applyCreateResponse;
    }

    public ApplyGetResponse getApply(Long applyId) {

        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.SESSION_UNAUTHORIZED));

        ApplyGetResponse applyGetResponse = ApplyConverter.toApplyGetResponse(apply);
        return applyGetResponse;
    }

    public ApplyListResponse getApplyList() {

        List<Apply> applies = applyRepository.findAll();
        List<ApplyItemResponse> applyItemResponses = new ArrayList<>();

        for (Apply apply : applies) {
            ApplyItemResponse applyItemResponse = ApplyItemResponse.builder()
                    .id(apply.getId())
                    .name(apply.getName())
                    .phone_num(apply.getPhone_num())
                    .birth_date(apply.getBirth_date())
                    .gender(apply.getGender())
                    .address(apply.getAddress())
                    .major(apply.getMajor())
                    .first_preference(apply.getFirst_preference())
                    .second_preference(apply.getSecond_preference())
                    .build();

            applyItemResponses.add(applyItemResponse);
        }

        return new ApplyListResponse(applyItemResponses);
    }
}
