package kahlua.KahluaProject.converter;

import kahlua.KahluaProject.domain.apply.Apply;
import kahlua.KahluaProject.dto.apply.request.ApplyCreateRequest;
import kahlua.KahluaProject.dto.apply.response.ApplyCreateResponse;
import kahlua.KahluaProject.dto.apply.response.ApplyGetResponse;

public class ApplyConverter {

    public static Apply toApply(ApplyCreateRequest applyCreateRequest) {
        return Apply.builder()
                .name(applyCreateRequest.getName())
                .phone_num(applyCreateRequest.getPhone_num())
                .birth_date(applyCreateRequest.getBirth_date())
                .gender(applyCreateRequest.getGender())
                .address(applyCreateRequest.getAddress())
                .major(applyCreateRequest.getMajor())
                .first_preference(applyCreateRequest.getFirst_preference())
                .second_preference(applyCreateRequest.getSecond_preference())
                .experience_and_reason(applyCreateRequest.getExperience_and_reason())
                .play_instrument(applyCreateRequest.getPlay_instrument())
                .motive(applyCreateRequest.getMotive())
                .finish_time(applyCreateRequest.getFinish_time())
                .meeting(applyCreateRequest.getMeeting())
                .readiness(applyCreateRequest.getReadiness())
                .build();
    }

    public static ApplyCreateResponse toApplyCreateResponse(Apply apply) {
        return ApplyCreateResponse.builder()
                .id(apply.getId())
                .name(apply.getName())
                .phone_num(apply.getPhone_num())
                .birth_date(apply.getBirth_date())
                .gender(apply.getGender())
                .address(apply.getAddress())
                .major(apply.getMajor())
                .first_preference(apply.getFirst_preference())
                .second_preference(apply.getSecond_preference())
                .experience_and_reason(apply.getExperience_and_reason())
                .play_instrument(apply.getPlay_instrument())
                .motive(apply.getMotive())
                .finish_time(apply.getFinish_time())
                .meeting(apply.getMeeting())
                .readiness(apply.getReadiness())
                .build();
    }

    public static ApplyGetResponse toApplyGetResponse(Apply apply) {
        return ApplyGetResponse.builder()
                .id(apply.getId())
                .name(apply.getName())
                .phone_num(apply.getPhone_num())
                .birth_date(apply.getBirth_date())
                .gender(apply.getGender())
                .address(apply.getAddress())
                .major(apply.getMajor())
                .first_preference(apply.getFirst_preference())
                .second_preference(apply.getSecond_preference())
                .experience_and_reason(apply.getExperience_and_reason())
                .play_instrument(apply.getPlay_instrument())
                .motive(apply.getMotive())
                .finish_time(apply.getFinish_time())
                .meeting(apply.getMeeting())
                .readiness(apply.getReadiness())
                .build();
    }
}
