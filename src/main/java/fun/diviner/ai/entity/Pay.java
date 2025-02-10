package fun.diviner.ai.entity;

import lombok.Data;

@Data
public class Pay {
    private PayType type;
    private int reward;
    private int recharge;
    private boolean state;
}