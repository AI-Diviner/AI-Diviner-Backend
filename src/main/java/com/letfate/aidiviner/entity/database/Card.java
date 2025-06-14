package com.letfate.aidiviner.entity.database;

import lombok.Data;

@Data
public class Card {
    private int id;
    private String code;
    private int point;
    private int number;
    private int count;
}