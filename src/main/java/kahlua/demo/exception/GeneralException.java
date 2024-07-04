package kahlua.demo.exception;

import kahlua.demo.apipayload.code.ReasonDto;
import kahlua.demo.apipayload.code.status.ErrorStatus;
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
