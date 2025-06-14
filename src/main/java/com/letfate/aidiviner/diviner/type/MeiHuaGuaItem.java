package com.letfate.aidiviner.diviner.type;

import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-20
 * @packageName fun.diviner.diviner
 **/

@Data
public class MeiHuaGuaItem {
    private String name;
    private String guaci;
    private String shanggua;
    private String xiagua;
    private List<String> yao;
}