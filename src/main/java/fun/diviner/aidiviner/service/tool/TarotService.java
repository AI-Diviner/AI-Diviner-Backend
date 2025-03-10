package fun.diviner.aidiviner.service.tool;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fun.diviner.aidiviner.diviner.Tarot;
import fun.diviner.aidiviner.diviner.type.TarotResponse;
import fun.diviner.aidiviner.dto.tool.tarot.TarotCommonParameter;
import fun.diviner.aidiviner.dto.tool.tarot.TarotSelectParameter;
import fun.diviner.aidiviner.service.UtilService;
import fun.diviner.aidiviner.util.response.ExceptionResponse;
import fun.diviner.aidiviner.util.response.ExceptionResponseCode;
import fun.diviner.aidiviner.util.response.GenerateResponse;

/**
 * @author Coaixy
 * @createTime 2025-01-24
 * @packageName fun.diviner.service.tool
 **/

@Service
public class TarotService {
    @Autowired
    private Tarot tarot;
    @Autowired
    private UtilService util;

    public GenerateResponse<TarotResponse> common(TarotCommonParameter parameter, boolean isPro) {
        try {
            String question = parameter.getQuestion();
            String spread = this.tarot.getTarotSpread(question);
            spread = formatSpreadName(spread);
            int needNum = this.tarot.getTarotNeedNum(spread);
            TarotResponse result = this.tarot.getTarotAnswer(question, spread, needNum, isPro);

            // 防止返回空，以后可以去掉
            if (result.getAnswer() == null || result.getAnswer().isEmpty()) {
                throw new Exception();
            }

            this.util.record(question, result.getAnswer());
            return new GenerateResponse<>(result);
        } catch (Exception error) {
            this.util.refund();
            throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "这个问题我不能回答你");
        }
    }

    private String formatSpreadName(String spreadName) {
        if (spreadName.equals("foundations")) {
            return "foundation";
        }
        if (spreadName.contains(":")) {
            return spreadName.split(":")[1];
        }
        if (spreadName.contains("：")) {
            return spreadName.split("：")[1];
        }
        return spreadName;
    }

    public GenerateResponse<TarotResponse> select(TarotSelectParameter parameter, boolean isPro) {
        try {
            TarotResponse result = this.tarot.getTarotAnswerBySelectIndex(parameter.getQuestion(), parameter.getSpreadName(), parameter.getCardIndexList(), isPro);

            // 防止返回空，以后可以去掉
            if (result.getAnswer() == null || result.getAnswer().isEmpty()) {
                throw new Exception();
            }

            this.util.record(parameter.getQuestion(), result.getAnswer());
            return new GenerateResponse<>(result);
        } catch (Exception error) {
            this.util.refund();
            throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "回答出错");
        }
    }
}