package fun.diviner.ai.diviner.type;

import java.util.List;

import lombok.Getter;

/**
 * { name: '年柱', heavenlyStems: '甲', earthlyBranches: '子', hidden: ['癸'], deities: '偏印', godOfDestiny: '天乙贵人' },
 */
@Getter
public class BaZiArrangeItem {
    private String name;
    private String heavenlyStems;
    private String earthlyBranches;
    private List<String> hidden;
    private String deities;
    private List<String> fuxing;

    public BaZiArrangeItem(String name, String heavenlyStems, String earthlyBranches, List<String> hidden, String deities, List<String> fuxing) {
        this.name = name;
        this.heavenlyStems = heavenlyStems;
        this.earthlyBranches = earthlyBranches;
        this.hidden = hidden;
        this.deities = deities;
        this.fuxing = fuxing;
    }
}