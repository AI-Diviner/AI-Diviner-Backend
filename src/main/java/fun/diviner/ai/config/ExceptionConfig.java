package fun.diviner.ai.config;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import fun.diviner.ai.util.response.GenerateResponse;
import fun.diviner.ai.util.response.ExceptionResponse;
import fun.diviner.ai.util.response.ExceptionResponseCode;

@RestControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GenerateResponse<String> noHandlerFoundExceptionHandle() {
        return new GenerateResponse<>(404, "未找到页面");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenerateResponse<String> internalServerErrorHandle() {
        return new GenerateResponse<>(500, "未知错误");
    }

    @ExceptionHandler(ExceptionResponse.class)
    public GenerateResponse<String> ExceptionResponseHandle(ExceptionResponse error) {
        return new GenerateResponse<>(error.getCode().getCode(), error.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenerateResponse<String>> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException error) {
        if (!error.getFieldError().getClass().toString().contains(SpringValidatorAdapter.class.toString())) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GenerateResponse<>(500, "未知错误"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new GenerateResponse<>(ExceptionResponseCode.PARAMETER_ERROR.getCode(), error.getAllErrors().get(0).getDefaultMessage()));
    }
}
