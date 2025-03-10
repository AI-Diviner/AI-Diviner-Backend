package fun.diviner.aidiviner.dto.tool.meihua;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

import fun.diviner.aidiviner.diviner.type.MeiHuaArrangeResponse;

/**
 * @author Coaixy
 * @createTime 2024-11-22
 * @packageName fun.diviner.dto.tool.meihua
 **/

@Data
public class MeiHuaSolveParameter {
    @NotNull(message="卦局不能为空")
    MeiHuaArrangeResponse arrangeResponse;

    @NotEmpty(message="问题不能为空")
    String question;
}