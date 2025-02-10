package fun.diviner.ai.diviner.ai;

import lombok.Getter;

/**
 * @author Coaixy
 * @createTime 2025-01-23
 * @packageName fun.diviner.diviner.Ai
 **/


@Getter
public enum AIModel {
    DEEPSEEK_REPONSER(
            "deepseek-reasoner",
            "https://api.deepseek.com",
            ""
    ),
    DEEPSEEK_REPONSER_OPEN(
            "deepseek/deepseek-r1",
            "https://openrouter.ai/api/v1/",
            ""
    ),
    GLM_FLASH(
            "glm-4-flash",
            "https://open.bigmodel.cn/api/paas/v4/",
            ""
    );

    private final String modelName;
    private final String baseApi;
    private final String key;

    AIModel(String modelName, String baseApi, String key) {
        this.modelName = modelName;
        this.baseApi = baseApi;
        this.key = key;
    }

}