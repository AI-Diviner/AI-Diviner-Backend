package fun.diviner.ai.diviner.type;

import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-29
 * @packageName fun.diviner.diviner.type
 **/

@Data
public class LiuYaoArrangeResponse {
    private List<String> bazi;
    private List<String> xunkong;
    private LiuYaoItem ben;
    private LiuYaoItem bian;
    private List<String> dong;
}