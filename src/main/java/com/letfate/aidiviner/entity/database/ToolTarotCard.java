package com.letfate.aidiviner.entity.database;

import lombok.Data;

@Data
public class ToolTarotCard {
    private int id;
    private String name;
    private String description;
    private String normal;
    private String reversed;
    private String detail;
    private String link;
}