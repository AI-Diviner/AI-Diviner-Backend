package com.letfate.aidiviner.dto.tool.bazi;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BaZiSolveParameter {
    @NotEmpty(message="性别不能为空")
    private String gender;

    @NotEmpty(message="八字不能为空")
    private String bazi;

    @NotNull(message="大运不能为空")
    private String dayun;

    @NotNull(message="流年不能为空")
    private String liunian;
}