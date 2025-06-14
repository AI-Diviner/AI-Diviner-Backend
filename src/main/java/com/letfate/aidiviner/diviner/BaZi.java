package com.letfate.aidiviner.diviner;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.letfate.aidiviner.diviner.ai.AIModel;
import com.letfate.aidiviner.diviner.ai.AIRequestUtil;
import com.letfate.aidiviner.diviner.bazi.BaZiSetting;
import com.letfate.aidiviner.diviner.bazi.BaZiTool;
import com.letfate.aidiviner.dto.tool.bazi.BaZiArrangeParameter;
import com.letfate.aidiviner.dto.tool.bazi.BaZiSolveParameter;

/**
 * @author Coaixy
 * @createTime 2025-01-23
 * @packageName fun.diviner.diviner
 **/


public class BaZi {
    public static String getAIResponse(BaZiSolveParameter obj) {
        String prompt = "";
        if (!obj.getDayun().equalsIgnoreCase("") && !obj.getLiunian().equalsIgnoreCase("")) {
            prompt = "你是一个命理大师 请你解析八字 %s %s 在 %s 大运 %s 流年的利弊 并给出合适的建议".formatted(
                    obj.getBazi(), obj.getGender(), obj.getDayun(), obj.getLiunian()
            );
        } else {
            prompt = "你是一个命理大师 请你解析八字 %s %s给出层次如何 不需要进行大运的解析 但给出适合的大运流年 最后给出一首诗来"
                    .formatted(obj.getBazi(), obj.getGender());
        }
        return AIRequestUtil.getAIResponse(prompt, AIModel.DEEPSEEK_REPONSER).getContent();
    }

    public static BaZiTool getBaZiObj(BaZiArrangeParameter parameter) {
        BaZiSetting baZiSetting = new BaZiSetting();

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        calendar.set(parameter.getYear(), parameter.getMonth() - 1, parameter.getDay(),
                parameter.getHour(), parameter.getMinute());
        Date date = calendar.getTime();

        baZiSetting.setName(null);
        baZiSetting.setDateType(0);
        baZiSetting.setDate(date);
        baZiSetting.setSex("男".equals(parameter.getGender()) ? 1 : 0);

        BaZiTool baZiTool = new BaZiTool(baZiSetting);
        baZiTool.setLunar(null);
        baZiTool.setSolar(null);
        return baZiTool;
    }
}