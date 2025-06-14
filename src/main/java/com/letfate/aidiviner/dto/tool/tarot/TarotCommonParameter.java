package com.letfate.aidiviner.dto.tool.tarot;

import jakarta.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class TarotCommonParameter {
    @NotEmpty(message="问题不能为空")
    private String question;
}