package fun.diviner.ai.util.response;

import lombok.Getter;

@Getter
public enum ExceptionResponseCode {
    PARAMETER_ERROR(110),
    USER_ERROR(120),
    RUN_ERROR(130);

    private int code;

    ExceptionResponseCode(int code) {
        this.code = code;
    }
}