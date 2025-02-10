package fun.diviner.ai.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

import fun.diviner.ai.entity.Special;

@Data
public class RechargeParameter {
    @NotNull(message="积分不能为空")
    @Min(value=Special.moneyPointProportion, message="积分仅能为" + Special.moneyPointProportion + "~" + 1000 * Special.moneyPointProportion + "的整数")
    @Max(value=1000 * Special.moneyPointProportion, message="积分仅能为" + Special.moneyPointProportion + "~" + 1000 * Special.moneyPointProportion + "的整数")
    private Integer point;

    @NotEmpty(message="类型不能为空")
    @Pattern(regexp="ali|wechat", message="类型仅能为ali,wechat,ali代表支付宝,wechat代表微信")
    private String type;
}