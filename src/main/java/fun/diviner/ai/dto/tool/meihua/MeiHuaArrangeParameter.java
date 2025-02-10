package fun.diviner.ai.dto.tool.meihua;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-20
 * @packageName fun.diviner.dto.tool.meihua
 **/

@Data
public class MeiHuaArrangeParameter {
    @NotEmpty(message="类型不能为空")
    private String type;

    @Min(value=10, message="数字仅能为10~2147483647的整数")
    @Max(value=2147483647, message="数字仅能为10~2147483647的整数")
    private int number;
}