package fun.diviner.ai.dto.tool.bazi;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class BaZiArrangeParameter {
    @NotNull(message="年不能为空")
    private Integer year;

    @NotNull(message="月不能为空")
    private Integer month;

    @NotNull(message="日不能为空")
    private Integer day;

    @NotNull(message="时不能为空")
    private Integer hour;

    @NotNull(message="分不能为空")
    private Integer minute;

    @NotEmpty(message="性别不能为空")
    private String gender;
}