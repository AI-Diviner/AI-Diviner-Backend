package com.letfate.aidiviner.service;

import java.util.UUID;
import java.util.Map;
import java.util.List;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.letfate.aidiviner.redis.CaptchaRedis;
import com.letfate.aidiviner.mapper.ToolMapper;
import com.letfate.aidiviner.mapper.GroupMapper;
import com.letfate.aidiviner.mapper.RechargeMapper;
import com.letfate.aidiviner.mapper.UserMapper;
import com.letfate.aidiviner.mapper.RecordMapper;
import com.letfate.aidiviner.util.response.GenerateResponse;
import com.letfate.aidiviner.util.response.ExceptionResponse;
import com.letfate.aidiviner.util.response.ExceptionResponseCode;
import com.letfate.aidiviner.util.Auxiliary;
import com.letfate.aidiviner.util.yi_pay.YiPay;
import com.letfate.aidiviner.entity.database.Tool;
import com.letfate.aidiviner.entity.database.Group;
import com.letfate.aidiviner.entity.database.Recharge;
import com.letfate.aidiviner.entity.database.User;
import com.letfate.aidiviner.entity.database.Record;
import com.letfate.aidiviner.entity.Special;
import com.letfate.aidiviner.dto.common.GetRecordIdParameter;
import com.letfate.aidiviner.dto.common.GetRecordParameter;

@Service
public class CommonService {
    @Autowired
    private CaptchaRedis captcha;
    @Autowired
    private ToolMapper tool;
    @Autowired
    private GroupMapper group;
    @Autowired
    private UtilService util;
    @Autowired
    private RechargeMapper recharge;
    @Autowired
    private UserMapper user;
    @Autowired
    private RecordMapper record;

    public GenerateResponse<Map<String, String>> getCaptcha() {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(150, 50, 4, 5);
        String key = UUID.randomUUID().toString();
        this.captcha.set(key, captcha.getCode());
        return new GenerateResponse<>(Map.of(
            "key", key,
            "image", "data:image/png;base64," + captcha.getImageBase64()
        ));
    }

    public GenerateResponse<List<Tool>> getTool() {
        return new GenerateResponse<>(this.tool.selectList(null));
    }

    public GenerateResponse<List<Map<String, Object>>> getVIP() {
        LambdaQueryWrapper<Group> wrapper = new LambdaQueryWrapper<>();
        wrapper.ne(Group::getId, Special.commonGroupId);
        List<Map<String, Object>> results = this.group.selectList(wrapper).stream().map(item -> {
            return Map.<String, Object>of(
                "id", item.getId(),
                "name", item.getName(),
                "checkInPoint", item.getCheckInPoint(),
                "price", item.getPrice()
            );
        }).toList();
        return new GenerateResponse<>(results);
    }

    public GenerateResponse<String> yiPayCallback(Map<String, String> parameter) {
        if (!YiPay.verify(util.getCoreContent(Special.CoreName.yiPayPlatformPublicKey), parameter)) {
            return new GenerateResponse<>("success");
        }

        LambdaQueryWrapper<Recharge> rechargeWrapper = new LambdaQueryWrapper<>();
        rechargeWrapper.eq(Recharge::getId, parameter.get("out_trade_no"));
        Recharge recharge = this.recharge.selectOne(rechargeWrapper);
        if (!recharge.isState()) {
            recharge.setState(true);
            recharge.setPaymentTime(LocalDateTime.now());
            this.recharge.updateById(recharge);

            User user = this.user.selectById(recharge.getUserId());
            user.setRechargeBalance(user.getRechargeBalance() + recharge.getPoint());
            this.user.updateById(user);
        }
        return new GenerateResponse<>("success");
    }

    public GenerateResponse<Map<String, Object>> getRecordId(GetRecordIdParameter parameter) {
        int pageSize = parameter.getPageSize();
        int pageNumber = parameter.getPageNumber();

        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Record::getTime);
        Page<Record> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);

        Page<Record> data = this.record.selectPage(page, wrapper);
        return new GenerateResponse<>(Map.of(
            "data", data.getRecords().stream().map(Record::getId).toList(),
            "total", data.getTotal()
        ));
    }

    public GenerateResponse<Map<String, Object>> getRecord(GetRecordParameter parameter) {
        String id = parameter.getId();

        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Record::getId, id);
        Record data = this.record.selectOne(wrapper);
        if (data == null) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "ID不存在");
        }

        Tool tool = this.tool.selectById(data.getToolId());
        return new GenerateResponse<>(Map.of(
            "tool", Map.of(
                "name", tool.getName(),
                "alias", tool.getAlias()
            ),
            "request", data.getRequest(),
            "response", data.getResponse(),
            "time", Auxiliary.localDateTimeToTimestamp(data.getTime())
        ));
    }
}