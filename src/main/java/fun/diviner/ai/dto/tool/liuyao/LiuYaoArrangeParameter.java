package fun.diviner.ai.dto.tool.liuyao;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-27
 * @packageName fun.diviner.dto.tool.liuyao
 **/

@Data
public class LiuYaoArrangeParameter {
    @NotEmpty(message="数据不能为空")
    private List<Integer> data;
}