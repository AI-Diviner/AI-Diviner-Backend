package fun.diviner.aidiviner.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;

import fun.diviner.aidiviner.service.UserService;
import fun.diviner.aidiviner.util.response.GenerateResponse;
import fun.diviner.aidiviner.util.response.ExceptionResponse;
import fun.diviner.aidiviner.util.response.ExceptionResponseCode;
import fun.diviner.aidiviner.dto.user.RegisterLoginParameter;
import fun.diviner.aidiviner.dto.user.ModifyAccountParameter;
import fun.diviner.aidiviner.dto.user.OpenVIPParameter;
import fun.diviner.aidiviner.dto.user.RechargeParameter;
import fun.diviner.aidiviner.dto.user.GetRechargeParameter;
import fun.diviner.aidiviner.dto.user.GetRecordParameter;
import fun.diviner.aidiviner.dto.user.UseCardParameter;
import fun.diviner.aidiviner.entity.Special;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/register_login")
    public GenerateResponse<Map<String, String>> registerLogin(@RequestBody @Validated RegisterLoginParameter parameter) {
        return this.service.registerLogin(parameter);
    }

    @GetMapping("/logout")
    public GenerateResponse<String> logout() {
        return this.service.logout();
    }

    @GetMapping("/get_base_information")
    public GenerateResponse<Map<String, Object>> getBaseInformation() {
        return this.service.getBaseInformation();
    }

    @PostMapping("/modify_account")
    public GenerateResponse<String> modifyAccount(@RequestBody @Validated ModifyAccountParameter parameter) {
        return this.service.modifyAccount(parameter);
    }

    @GetMapping("/check_in")
    public GenerateResponse<String> checkIn() {
        return this.service.checkIn();
    }

    @PostMapping("/open_vip")
    public GenerateResponse<String> openVIP(@RequestBody @Validated OpenVIPParameter parameter) {
        int id = parameter.getId();
        if (id == Special.commonGroupId) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "ID不能为" + Special.commonGroupId);
        }
        
        return this.service.openVIP(parameter);
    }

    @PostMapping("/recharge")
    public GenerateResponse<String> recharge(@RequestBody @Validated RechargeParameter parameter) {
        return this.service.recharge(parameter);
    }

    @GetMapping("/get_recharge")
    public GenerateResponse<Map<String, Object>> getRecharge(@Validated GetRechargeParameter parameter) {
        return this.service.getRecharge(parameter);
    }

    @GetMapping("/get_record")
    public GenerateResponse<Map<String, Object>> getRecord(@Validated GetRecordParameter parameter) {
        return this.service.getRecord(parameter);
    }

    @PostMapping("/use_card")
    public GenerateResponse<Integer> useCard(@RequestBody @Validated UseCardParameter parameter) {
        return this.service.useCard(parameter);
    }
}