package com.letfate.aidiviner.dto.common;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class GetRecordIdParameter {
    @Min(value=1, message="每页数量仅能大于1")
    private int pageSize = 1000;

    @Min(value=1, message="页码仅能大于1")
    private int pageNumber = 1;
}