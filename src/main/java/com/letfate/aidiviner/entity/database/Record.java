package com.letfate.aidiviner.entity.database;

import java.time.LocalDateTime;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;

@Data
public class Record {
    private String id;
    private int toolId;
    private String request;
    private String response;

    @TableField(value="time_", insertStrategy=FieldStrategy.NEVER)
    private LocalDateTime time;

    private int userId;
}