package fun.diviner.aidiviner.service;

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

import fun.diviner.aidiviner.redis.CaptchaRedis;
import fun.diviner.aidiviner.mapper.ToolMapper;
import fun.diviner.aidiviner.mapper.GroupMapper;
import fun.diviner.aidiviner.mapper.RechargeMapper;
import fun.diviner.aidiviner.mapper.UserMapper;
import fun.diviner.aidiviner.mapper.RecordMapper;
import fun.diviner.aidiviner.util.response.GenerateResponse;
import fun.diviner.aidiviner.util.response.ExceptionResponse;
import fun.diviner.aidiviner.util.response.ExceptionResponseCode;
import fun.diviner.aidiviner.util.Auxiliary;
import fun.diviner.aidiviner.util.yi_pay.YiPay;
import fun.diviner.aidiviner.entity.database.Tool;
import fun.diviner.aidiviner.entity.database.Group;
import fun.diviner.aidiviner.entity.database.Recharge;
import fun.diviner.aidiviner.entity.database.User;
import fun.diviner.aidiviner.entity.database.Record;
import fun.diviner.aidiviner.entity.Special;
import fun.diviner.aidiviner.dto.common.GetRecordIdParameter;
import fun.diviner.aidiviner.dto.common.GetRecordParameter;

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