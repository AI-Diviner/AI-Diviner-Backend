package fun.diviner.ai.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import fun.diviner.ai.service.tool.TarotService;
import fun.diviner.ai.service.tool.BaZiService;
import fun.diviner.ai.service.tool.MeiHuaService;
import fun.diviner.ai.util.response.GenerateResponse;
import fun.diviner.ai.diviner.type.TarotResponse;
import fun.diviner.ai.diviner.type.BaZiArrangeResponse;
import fun.diviner.ai.diviner.type.MeiHuaArrangeResponse;
import fun.diviner.ai.dto.tool.tarot.TarotCommonParameter;
import fun.diviner.ai.dto.tool.tarot.TarotSelectParameter;
import fun.diviner.ai.dto.tool.bazi.BaZiArrangeParameter;
import fun.diviner.ai.dto.tool.bazi.BaZiSolveParameter;
import fun.diviner.ai.dto.tool.meihua.MeiHuaArrangeParameter;
import fun.diviner.ai.dto.tool.meihua.MeiHuaSolveParameter;

@RestController
@RequestMapping("/tool")
public class ToolController {
    @Autowired
    private TarotService tarot;
    @Autowired
    private BaZiService baZi;
    @Autowired
    private MeiHuaService meiHua;

    @PostMapping("/tarot/common")
    public GenerateResponse<TarotResponse> tarotCommon(@RequestBody @Validated TarotCommonParameter parameter) {
        return this.tarot.common(parameter, false);
    }

    @PostMapping("/tarot/select")
    public GenerateResponse<TarotResponse> tarotSelect(@RequestBody @Validated TarotSelectParameter parameter) {
        return this.tarot.select(parameter, false);
    }

    @PostMapping("/tarot/common_pro")
    public GenerateResponse<TarotResponse> tarotCommonPro(@RequestBody @Validated TarotCommonParameter parameter) {
        return this.tarot.common(parameter, true);
    }

    @PostMapping("/tarot/select_pro")
    public GenerateResponse<TarotResponse> tarotSelectPro(@RequestBody @Validated TarotSelectParameter parameter) {
        return this.tarot.select(parameter, true);
    }

    @PostMapping("/bazi/arrange")
    public GenerateResponse<BaZiArrangeResponse> baZiArrange(@RequestBody @Validated BaZiArrangeParameter parameter) {
        return this.baZi.arrange(parameter);
    }

    @PostMapping("/bazi/solve")
    public GenerateResponse<String> baZiSolve(@RequestBody @Validated BaZiSolveParameter parameter) {
        return this.baZi.solve(parameter);
    }

    @PostMapping("/meihua/arrange")
    public GenerateResponse<MeiHuaArrangeResponse> meiHuaArrange(@RequestBody @Validated MeiHuaArrangeParameter parameter) {
        return this.meiHua.arrange(parameter);
    }

    @PostMapping("/meihua/solve")
    public GenerateResponse<String> meiHuaSolve(@RequestBody @Validated MeiHuaSolveParameter parameter) {
        return this.meiHua.solve(parameter);
    }
}