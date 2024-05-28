package kahlua.demo.service;

import jakarta.transaction.Transactional;
import kahlua.demo.apipayload.code.status.ErrorStatus;
import kahlua.demo.converter.ApplyConverter;
import kahlua.demo.domain.apply.Apply;
import kahlua.demo.dto.request.ApplyCreateRequest;
import kahlua.demo.dto.response.ApplyCreateResponse;
import kahlua.demo.dto.response.ApplyGetResponse;
import kahlua.demo.exception.GeneralException;
import kahlua.demo.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
