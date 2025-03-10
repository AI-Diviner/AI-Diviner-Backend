package fun.diviner.aidiviner.diviner.type;

import java.util.List;

import lombok.Data;

/**
 * @author Coaixy
 * @createTime 2024-11-09
 * @packageName fun.diviner.diviner
 **/

@Data
public class BaZiArrangeResponse {
    private BaZiArrangeItem yearPillar;
    private BaZiArrangeItem monthPillar;
    private BaZiArrangeItem dayPillar;
    private BaZiArrangeItem hourPillar;
    private List<String> baZiWuXingCount;
    private List<String> baZiwuXingWangShuai;
    private String bodyIntensity;
    private String guZhong;
    private String guZhongPiZhu;
    private String dayZhuLunMing;
    private String yinYuan;
    private String wuXingFenXi;
    private List<List<String>> dayun;
    private List<List<String>> liuNian;
}