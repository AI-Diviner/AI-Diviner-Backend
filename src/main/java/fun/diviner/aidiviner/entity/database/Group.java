package fun.diviner.aidiviner.entity.database;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user_group")
@Data
public class Group {
    private int id;
    private String name;
    private int checkInPoint;
    private int price;
}