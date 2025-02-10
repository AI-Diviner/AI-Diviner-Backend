package fun.diviner.ai.util.response;

import lombok.Getter;

@Getter
public class GenerateResponse<T> {
    private int code;
    private String message;
    private T data;

    public GenerateResponse(int code, String message){
        this.code = code;
        this.message = message;
    }

    public GenerateResponse(T data){
        this.code = 200;
        this.message = "success";
        this.data = data;
    }
}