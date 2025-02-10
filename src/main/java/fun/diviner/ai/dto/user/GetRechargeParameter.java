package fun.diviner.ai.dto.user;

import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class GetRechargeParameter {
    @Min(value=1, message="每页数量仅能大于1")
    private int pageSize = 30;

    @Min(value=1, message="页码仅能大于1")
    private int pageNumber = 1;
}