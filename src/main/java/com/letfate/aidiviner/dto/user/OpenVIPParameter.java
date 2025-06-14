package com.letfate.aidiviner.dto.user;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class OpenVIPParameter {
    @NotNull(message="ID不能为空")
    private Integer id;
}