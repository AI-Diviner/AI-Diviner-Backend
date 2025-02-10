package fun.diviner.ai.entity.database;

import lombok.Data;

@Data
public class ToolTarotSpread {
    private int id;
    private String name;
    private String description;
    private String card;
}