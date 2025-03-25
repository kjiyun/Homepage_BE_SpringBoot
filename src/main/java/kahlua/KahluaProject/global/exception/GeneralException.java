package kahlua.KahluaProject.global.exception;

import kahlua.KahluaProject.global.apipayload.code.ReasonDto;
import kahlua.KahluaProject.global.apipayload.code.status.ErrorStatus;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {

    private final ErrorStatus errorStatus;

    public GeneralException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
    }

    public ReasonDto getErrorStatus() {
        return this.errorStatus.getReason();
    }

}
