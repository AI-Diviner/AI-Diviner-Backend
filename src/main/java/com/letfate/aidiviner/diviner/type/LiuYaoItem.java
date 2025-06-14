package com.letfate.aidiviner.diviner.type;

import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-29
 * @packageName fun.diviner.diviner.type
 **/

@Data
public class LiuYaoItem {
    private String name;
    private List<String> yao;
    private List<String> gan;
    private List<String> wuxing;
    private List<String> liuqin;
    private List<String> liushou;
    private List<String> shiying;
}