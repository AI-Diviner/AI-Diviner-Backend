package fun.diviner.ai.controller;

import java.util.Map;
import java.util.List;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import fun.diviner.ai.service.CommonService;
import fun.diviner.ai.util.response.GenerateResponse;
import fun.diviner.ai.entity.database.Tool;
import fun.diviner.ai.dto.common.GetRecordIdParameter;
import fun.diviner.ai.dto.common.GetRecordParameter;

@RestController
@RequestMapping("/common")
public class CommonController {
    @Autowired
    private CommonService service;

    @GetMapping("/get_captcha")
    public GenerateResponse<Map<String, String>> getCaptcha() {
        return this.service.getCaptcha();
    }

    @GetMapping("/get_tool")
    public GenerateResponse<List<Tool>> getTool() {
        return this.service.getTool();
    }

    @GetMapping("/get_vip")
    public GenerateResponse<List<Map<String, Object>>> getVIP() {
        return this.service.getVIP();
    }

    @GetMapping("/yi_pay_callback")
    public GenerateResponse<String> yiPayCallback(@RequestParam Map<String, String> parameter) {
        return this.service.yiPayCallback(parameter);
    }

    @GetMapping("/get_record_id")
    public GenerateResponse<Map<String, Object>> getRecordUUID(@Validated GetRecordIdParameter parameter) {
        return this.service.getRecordId(parameter);
    }

    @GetMapping("/get_record")
    public GenerateResponse<Map<String, Object>> getRecord(@Validated GetRecordParameter parameter) {
        return this.service.getRecord(parameter);
    }
}