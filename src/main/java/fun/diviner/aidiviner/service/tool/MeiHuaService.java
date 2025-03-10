package fun.diviner.aidiviner.service.tool;

import java.util.Date;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fun.diviner.aidiviner.diviner.MeiHua;
import fun.diviner.aidiviner.diviner.meihua.MeiHuaObj;
import fun.diviner.aidiviner.diviner.meihua.MeiHuaSetting;
import fun.diviner.aidiviner.diviner.type.MeiHuaArrangeResponse;
import fun.diviner.aidiviner.diviner.type.MeiHuaGuaItem;
import fun.diviner.aidiviner.dto.tool.meihua.MeiHuaArrangeParameter;
import fun.diviner.aidiviner.dto.tool.meihua.MeiHuaSolveParameter;
import fun.diviner.aidiviner.service.UtilService;
import fun.diviner.aidiviner.util.response.GenerateResponse;

/**
 * @author Coaixy
 * @createTime 2025-01-25
 * @packageName fun.diviner.service.tool
 **/

@Service
public class MeiHuaService {
    @Autowired
    private UtilService util;

    public GenerateResponse<MeiHuaArrangeResponse> arrange(MeiHuaArrangeParameter parameter) {
        MeiHuaObj meiHuaObj;
        String type = parameter.getType();
        if (type.equalsIgnoreCase("time")) {
            meiHuaObj = new MeiHuaObj(new Date());
        } else if (type.equalsIgnoreCase("number")) {
            meiHuaObj = numberToMeiHuaObj(parameter.getNumber());
        } else {
            meiHuaObj = numberToMeiHuaObj(RandomUtil.randomInt(10, 2147483647));
        }
        MeiHuaArrangeResponse response = new MeiHuaArrangeResponse();
        response.setBazi(meiHuaObj.getBaZi());
        //本卦
        MeiHuaGuaItem ben = new MeiHuaGuaItem();
        ben.setName(meiHuaObj.getBenGua());
        ben.setGuaci(meiHuaObj.getBenGuaGuaCi());
        ben.setYao(meiHuaObj.getBenGuaLiuYaoAs());
        ben.setShanggua(meiHuaObj.getBenGuaShangGua());
        ben.setXiagua(meiHuaObj.getBenGuaXiaGua());
        response.setBen(ben);
        // 互卦
        MeiHuaGuaItem hu = new MeiHuaGuaItem();
        hu.setName(meiHuaObj.getHuGua());
        hu.setGuaci(meiHuaObj.getHuGuaGuaCi());
        hu.setYao(meiHuaObj.getHuGuaLiuYaoAs());
        hu.setShanggua(meiHuaObj.getHuGuaShangGua());
        hu.setXiagua(meiHuaObj.getHuGuaXiaGua());
        response.setHu(hu);
        // 变卦
        MeiHuaGuaItem bian = new MeiHuaGuaItem();
        bian.setName(meiHuaObj.getBianGua());
        bian.setGuaci(meiHuaObj.getBianGuaGuaCi());
        bian.setYao(meiHuaObj.getBianGuaLiuYaoAs());
        bian.setShanggua(meiHuaObj.getBianGuaShangGua());
        bian.setXiagua(meiHuaObj.getBianGuaXiaGua());
        response.setBian(bian);
        response.setDong(7 - meiHuaObj.getDongYaoNumber());

        return new GenerateResponse<>(response);

    }

    private MeiHuaObj numberToMeiHuaObj(int number) {

        MeiHuaSetting meiHuaSetting = new MeiHuaSetting();
        //数字起卦
        meiHuaSetting.setQiGuaMode(2);
        meiHuaSetting.setQiGuaDanNumber(number);
        return new MeiHuaObj(meiHuaSetting);
    }

    public GenerateResponse<String> solve(MeiHuaSolveParameter parameter) {
        try {
            String response = MeiHua.getAIResponse(parameter.getArrangeResponse(), parameter.getQuestion());

            // 防止返回空，以后可以去掉
            if (response == null || response.isEmpty()) {
                throw new Exception();
            }

            this.util.record(parameter.getQuestion(), response);
            return new GenerateResponse<>(response);
        } catch (Exception error) {
            this.util.refund();
            return new GenerateResponse<>("解卦出错");
        }
    }
}