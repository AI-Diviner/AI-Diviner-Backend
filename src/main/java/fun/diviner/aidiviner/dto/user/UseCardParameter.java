package fun.diviner.aidiviner.dto.user;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class UseCardParameter {
    @NotEmpty(message="码不能为空")
    private String code;
}