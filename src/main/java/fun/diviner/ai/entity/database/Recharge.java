package fun.diviner.ai.entity.database;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;

@Data
public class Recharge {
    private String id;
    private int point;
    private BigDecimal money;
    private String url;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private boolean state;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private LocalDateTime creationTime;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private LocalDateTime paymentTime;

    private int userId;
}