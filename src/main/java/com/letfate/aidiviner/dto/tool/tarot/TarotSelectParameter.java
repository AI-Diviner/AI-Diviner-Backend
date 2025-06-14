package com.letfate.aidiviner.dto.tool.tarot;

import jakarta.validation.constraints.NotEmpty;
import java.util.List;

import lombok.Data;

@Data
public class TarotSelectParameter {
    @NotEmpty(message="牌阵不能为空")
    private String spreadName;

    @NotEmpty(message="牌不能为空")
    private List<Integer> cardIndexList;

    @NotEmpty(message="问题不能为空")
    private String question;
}