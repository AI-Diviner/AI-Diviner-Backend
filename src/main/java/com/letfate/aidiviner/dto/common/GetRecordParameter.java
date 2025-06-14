package com.letfate.aidiviner.dto.common;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class GetRecordParameter {
    @NotEmpty(message="ID不能为空")
    private String id;
}