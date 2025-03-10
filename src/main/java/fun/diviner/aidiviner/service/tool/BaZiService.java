package fun.diviner.aidiviner.service.tool;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fun.diviner.aidiviner.diviner.BaZi;
import fun.diviner.aidiviner.diviner.bazi.BaZiTool;
import fun.diviner.aidiviner.diviner.type.BaZiArrangeItem;
import fun.diviner.aidiviner.diviner.type.BaZiArrangeResponse;
import fun.diviner.aidiviner.dto.tool.bazi.BaZiArrangeParameter;
import fun.diviner.aidiviner.dto.tool.bazi.BaZiSolveParameter;
import fun.diviner.aidiviner.service.UtilService;
import fun.diviner.aidiviner.util.response.ExceptionResponse;
import fun.diviner.aidiviner.util.response.ExceptionResponseCode;
import fun.diviner.aidiviner.util.response.GenerateResponse;

/**
 * @author Coaixy
 * @createTime 2025-01-23
 * @packageName fun.diviner.service.tool
 **/

@Service
public class BaZiService {
    @Autowired
    private UtilService util;

    public GenerateResponse<BaZiArrangeResponse> arrange(BaZiArrangeParameter parameter) {
        return new GenerateResponse<>(generateBaZiResponse(BaZi.getBaZiObj(parameter)));
    }

    /**
     * 返回八字分析结果
     *
     * @param obj Bazi对象
     * @return BaziResponse
     */
    private BaZiArrangeResponse generateBaZiResponse(BaZiTool obj) {
        BaZiArrangeResponse response = new BaZiArrangeResponse();
        //year
        BaZiArrangeItem yearItem = new BaZiArrangeItem("年柱", obj.getYearGan(), obj.getYearZhi(), obj.getYearZhiCangGan(), obj.getYearGanZhiZhuXing(), obj.getYearGanZhiFuXing());
        //month
        BaZiArrangeItem monthItem = new BaZiArrangeItem("月柱", obj.getMonthGan(), obj.getMonthZhi(), obj.getMonthZhiCangGan(), obj.getMonthGanZhiZhuXing(), obj.getMonthGanZhiFuXing());
        //day
        BaZiArrangeItem dayItem = new BaZiArrangeItem("日柱", obj.getDayGan(), obj.getDayZhi(), obj.getDayZhiCangGan(), obj.getDayGanZhiZhuXing(), obj.getDayGanZhiFuXing());
        //hour
        BaZiArrangeItem hourItem = new BaZiArrangeItem("时柱", obj.getHourGan(), obj.getHourZhi(), obj.getHourZhiCangGan(), obj.getHourGanZhiZhuXing(), obj.getHourGanZhiFuXing());

        response.setYearPillar(yearItem);
        response.setMonthPillar(monthItem);
        response.setDayPillar(dayItem);
        response.setHourPillar(hourItem);

        response.setBaZiWuXingCount(obj.getBaZiWuXingCount());
        response.setBaZiwuXingWangShuai(obj.getWuXingWangShuai());
        response.setBodyIntensity(obj.getBodyIntensity());
        response.setGuZhong(obj.getGuZhong());
        response.setGuZhongPiZhu(obj.getGuZhongPiZhu());
        response.setDayZhuLunMing(obj.getDayZhuLunMing());
        response.setYinYuan(obj.getYinYuan());
        response.setWuXingFenXi(obj.getWuXingFenXi());
        List<List<String>> daYun = obj.getDaYun();
        daYun.get(0).set(2, obj.getXiaoYun().get(0).get(2));
        response.setDayun(daYun);
        response.setLiuNian(obj.getLiuNian());

        return response;
    }

    public GenerateResponse<String> solve(BaZiSolveParameter parameter) {
        try {
            String response = BaZi.getAIResponse(parameter);

            // 防止返回空，以后可以去掉
            if (response == null || response.isEmpty()) {
                throw new Exception();
            }

            if (parameter.getDayun().isEmpty()) {
                this.util.record(parameter.getBazi(), response);
            } else {
                this.util.record(parameter.getBazi() + "\n大运:" + parameter.getDayun() + " 流年:"+ parameter.getLiunian(), response);
            }
            return new GenerateResponse<>(response);
        } catch (Exception error) {
            this.util.refund();
            throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "回答出错");
        }
    }
}