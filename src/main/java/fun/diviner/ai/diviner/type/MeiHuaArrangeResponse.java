package fun.diviner.ai.diviner.type;

import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-20
 * @packageName fun.diviner.diviner
 **/

@Data
public class MeiHuaArrangeResponse {
    private List<String> bazi;
    private MeiHuaGuaItem ben;
    private MeiHuaGuaItem hu;
    private MeiHuaGuaItem bian;
    private int dong;
}