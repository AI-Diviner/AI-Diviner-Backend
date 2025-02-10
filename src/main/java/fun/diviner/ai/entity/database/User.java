package fun.diviner.ai.entity.database;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
public class User {
    @TableId(type=IdType.AUTO)
    private int id;

    private String phoneNumber;
    private String password;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private int rewardBalance;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private int rechargeBalance;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private boolean checkIn;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private int groupId;

    @TableField(insertStrategy=FieldStrategy.NEVER)
    private LocalDateTime groupExpirationTime;
}