package com.letfate.aidiviner.diviner.ai;

import lombok.Getter;

/**
 * @author Coaixy
 * @createTime 2025-01-23
 * @packageName fun.diviner.diviner.Ai
 **/

@Getter
public class ReasoningResponse {
    private String content;
    private String reasoningContent;

    ReasoningResponse(String content, String reasoningContent) {
        this.content = content;
        this.reasoningContent = reasoningContent;
    }
}