package com.letfate.aidiviner.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import com.letfate.aidiviner.service.tool.TarotService;
import com.letfate.aidiviner.service.tool.BaZiService;
import com.letfate.aidiviner.service.tool.MeiHuaService;
import com.letfate.aidiviner.util.response.GenerateResponse;
import com.letfate.aidiviner.diviner.type.TarotResponse;
import com.letfate.aidiviner.diviner.type.BaZiArrangeResponse;
import com.letfate.aidiviner.diviner.type.MeiHuaArrangeResponse;
import com.letfate.aidiviner.dto.tool.tarot.TarotCommonParameter;
import com.letfate.aidiviner.dto.tool.tarot.TarotSelectParameter;
import com.letfate.aidiviner.dto.tool.bazi.BaZiArrangeParameter;
import com.letfate.aidiviner.dto.tool.bazi.BaZiSolveParameter;
import com.letfate.aidiviner.dto.tool.meihua.MeiHuaArrangeParameter;
import com.letfate.aidiviner.dto.tool.meihua.MeiHuaSolveParameter;

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