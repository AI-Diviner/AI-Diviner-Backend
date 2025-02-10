package fun.diviner.ai.dto.tool.xiaoliuren;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-12-02
 * @packageName fun.diviner.dto.tool.xiaoliu
 **/

@Data
public class XiaoLiuRenSolveParameter {
    @NotEmpty(message = "问题不能为空")
    private String question;

    @NotEmpty(message = "神不能为空")
    @Size(min=3, max=3, message="神的长度仅能为3")
    private List<String> liushen;
}