package fun.diviner.aidiviner.entity.database;

import lombok.Data;

@Data
public class Tool {
    private int id;
    private String name;
    private String alias;
    private int price;
}