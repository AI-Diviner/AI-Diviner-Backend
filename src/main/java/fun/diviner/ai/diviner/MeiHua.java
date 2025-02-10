package fun.diviner.ai.diviner;

import fun.diviner.ai.diviner.ai.AIModel;
import fun.diviner.ai.diviner.ai.AIRequestUtil;
import fun.diviner.ai.diviner.type.MeiHuaArrangeResponse;

/**
 * @author Coaixy
 * @createTime 2025-01-25
 * @packageName fun.diviner.diviner
 **/

public class MeiHua {
    public static String getAIResponse(MeiHuaArrangeResponse arrangeResponse, String question) {
        String prompt = """
                你是一个梅花易数大师
                请你帮我解析以下梅花易数排盘结果
                时间: %s
                卦局: %s 变 %s
                问题: %s
                """.formatted(arrangeResponse.getBazi(), arrangeResponse.getBen().getName(), arrangeResponse.getBian().getName(), question);

        return AIRequestUtil.getAIResponse(prompt, AIModel.DEEPSEEK_REPONSER_OPEN).getContent();
    }
}