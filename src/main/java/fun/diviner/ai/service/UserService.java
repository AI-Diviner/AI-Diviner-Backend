package fun.diviner.ai.service;

import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;
import java.time.Instant;
import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.util.RandomUtil;

import fun.diviner.ai.mapper.UserMapper;
import fun.diviner.ai.mapper.GroupMapper;
import fun.diviner.ai.mapper.CoreMapper;
import fun.diviner.ai.mapper.RechargeMapper;
import fun.diviner.ai.mapper.ToolMapper;
import fun.diviner.ai.mapper.RecordMapper;
import fun.diviner.ai.mapper.CardMapper;
import fun.diviner.ai.mapper.CardRecordMapper;
import fun.diviner.ai.util.response.GenerateResponse;
import fun.diviner.ai.util.response.ExceptionResponse;
import fun.diviner.ai.util.response.ExceptionResponseCode;
import fun.diviner.ai.util.Auxiliary;
import fun.diviner.ai.util.yi_pay.YiPayType;
import fun.diviner.ai.util.yi_pay.YiPay;
import fun.diviner.ai.util.yi_pay.YiPayResponse;
import fun.diviner.ai.dto.user.RegisterLoginParameter;
import fun.diviner.ai.dto.user.ModifyAccountParameter;
import fun.diviner.ai.dto.user.OpenVIPParameter;
import fun.diviner.ai.dto.user.RechargeParameter;
import fun.diviner.ai.dto.user.GetRechargeParameter;
import fun.diviner.ai.dto.user.GetRecordParameter;
import fun.diviner.ai.dto.user.UseCardParameter;
import fun.diviner.ai.entity.database.User;
import fun.diviner.ai.entity.database.Group;
import fun.diviner.ai.entity.database.Core;
import fun.diviner.ai.entity.database.Recharge;
import fun.diviner.ai.entity.database.Record;
import fun.diviner.ai.entity.database.Card;
import fun.diviner.ai.entity.database.CardRecord;
import fun.diviner.ai.entity.Special;
import fun.diviner.ai.entity.Auth;
import fun.diviner.ai.entity.PayType;
import fun.diviner.ai.redis.AccessTokenRedis;
import fun.diviner.ai.config.Interceptor.auth.JWTUtil;

@Service
public class UserService {
    @Autowired
    private UserMapper user;
    @Autowired
    private AccessTokenRedis accessToken;
    @Autowired
    private UtilService util;
    @Autowired
    private GroupMapper group;
    @Autowired
    private CoreMapper core;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RechargeMapper recharge;
    @Autowired
    private ToolMapper tool;
    @Autowired
    private RecordMapper record;
    @Autowired
    private CardMapper card;
    @Autowired
    private CardRecordMapper cardRecord;

    public GenerateResponse<Map<String, String>> registerLogin(RegisterLoginParameter parameter) {
        String phoneNumber = parameter.getPhoneNumber();
        String password = parameter.getPassword();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhoneNumber, phoneNumber);
        User user = this.user.selectOne(wrapper);
        if (user == null) {
            user = new User();
            user.setPhoneNumber(phoneNumber);
            user.setPassword(password);
            this.user.insert(user);
        } else if (!password.equals(user.getPassword())) {
            throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "密码错误");
        }

        String accessToken = UUID.randomUUID().toString();
        this.accessToken.set(user.getId(), accessToken);
        return new GenerateResponse<>(Map.of(
            "accessToken", JWTUtil.generate(Map.of(
                "token", accessToken
            ), Special.RedisSurvivalTime.accessToken, TimeUnit.DAYS)
        ));
    }

    public GenerateResponse<String> logout() {
        this.accessToken.delete(((Auth) this.util.getAttribute(Special.AttributeName.user)).getToken());
        return new GenerateResponse<>("成功");
    }

    public GenerateResponse<Map<String, Object>> getBaseInformation() {
        User user = this.user.selectById(this.util.getUserId());
        Group group = this.group.selectById(user.getGroupId());
        Map<String, Object> groupResult = new HashMap<>(Map.of(
            "name", group.getName(),
            "checkInPoint", group.getCheckInPoint()
        ));
        groupResult.put("expirationTime", user.getGroupExpirationTime() == null ? null : Auxiliary.localDateTimeToTimestamp(user.getGroupExpirationTime()));
        return new GenerateResponse<>(Map.of(
            "phoneNumber", user.getPhoneNumber(),
            "balance", Map.of(
                "reward", user.getRewardBalance(),
                "recharge", user.getRechargeBalance()
            ),
            "checkIn", user.isCheckIn(),
            "group", groupResult
        ));
    }

    public GenerateResponse<String> modifyAccount(ModifyAccountParameter parameter) {
        String password = parameter.getPassword();

        User user = this.user.selectById(this.util.getUserId());
        if (password != null) {
            user.setPassword(password);
            this.accessToken.delete(this.util.getUserId());
        }
        this.user.updateById(user);
        return new GenerateResponse<>("成功");
    }

    public GenerateResponse<String> checkIn() {
        User user = this.user.selectById(this.util.getUserId());
        if (user.isCheckIn()) {
            throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "已签到");
        }

        Group group = this.group.selectById(user.getGroupId());
        user.setCheckIn(true);
        user.setRewardBalance(user.getRewardBalance() + group.getCheckInPoint());
        this.user.updateById(user);
        return new GenerateResponse<>("成功");
    }

    public GenerateResponse<String> openVIP(OpenVIPParameter parameter) {
        int id = parameter.getId();

        Group vipGroup = this.group.selectById(id);
        if (vipGroup == null) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "会员不存在");
        }
        User user = this.user.selectById(this.util.getUserId());
        Group userGroup = this.group.selectById(user.getGroupId());
        boolean expirationIsNull = user.getGroupExpirationTime() == null;
        boolean expiration = !expirationIsNull && user.getGroupExpirationTime().isBefore(LocalDateTime.now());
        if (user.getGroupId() != Special.commonGroupId) {
            if (expirationIsNull) {
                throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "当前永久会员,无法开通新会员");
            } else if (!expiration && vipGroup.getPrice() < userGroup.getPrice()) {
                throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "当前会员未过期,仅能开通大于等于当前会员价格的会员");
            }
        } else if (!this.util.pay(PayType.RECHARGE_BALANCE, vipGroup.getPrice()).isState()) {
            throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "余额不足");
        }

        user = this.user.selectById(this.util.getUserId());
        if (expirationIsNull || expiration) {
            user.setGroupExpirationTime(LocalDateTime.now().plusDays(31));
        } else {
            user.setGroupExpirationTime(user.getGroupExpirationTime().plusDays(31));
        }
        if (id != user.getGroupId()) {
            user.setGroupId(id);
        }
        this.user.updateById(user);
        return new GenerateResponse<>("成功");
    }

    public GenerateResponse<String> recharge(RechargeParameter parameter) {
        int point = parameter.getPoint();
        String type = parameter.getType();

        LambdaQueryWrapper<Core> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Core::getName, List.of(Special.CoreName.yiPayId, Special.CoreName.yiPayMerchantPrivateKey, Special.CoreName.yiPayNoticeUrlPrefix, Special.CoreName.yiPayReturnUrl));
        List<Core> data = this.core.selectList(wrapper);
        int platformId = Integer.parseInt(data.get(0).getContent());
        String merchantPrivateKey = data.get(1).getContent();
        String noticeUrlPrefix = data.get(2).getContent();
        String returnUrl = data.get(3).getContent();
        String tradeId = String.valueOf(this.util.getUserId()) + RandomUtil.randomInt(100, 1000) + Instant.now().getEpochSecond();
        String name = "充值" + point + "余额-" + tradeId;
        double money = (double) point / Special.moneyPointProportion;
        YiPayType payType = switch (type) {
            case "ali" -> YiPayType.ALI;
            case "wechat" -> YiPayType.WECHAT;
            default -> YiPayType.ALI;
        };

        YiPayResponse response;
        try {
            response = YiPay.pay(platformId, merchantPrivateKey, noticeUrlPrefix + "common/yi_pay_callback", returnUrl, tradeId, name, money, payType, this.request.getHeader("X-Real-IP") == null ? request.getRemoteAddr() : this.request.getHeader("X-Real-IP"));
            if (response.getCode() != 0) {
                throw new IOException();
            }
        } catch (IOException error) {
            throw new ExceptionResponse(ExceptionResponseCode.RUN_ERROR, "充值失败");
        }

        Recharge recharge = new Recharge();
        recharge.setId(tradeId);
        recharge.setPoint(point);
        recharge.setMoney(BigDecimal.valueOf(money));
        recharge.setUrl(response.getPay_info());
        recharge.setUserId(this.util.getUserId());
        this.recharge.insert(recharge);
        return new GenerateResponse<>(response.getPay_info());
    }

    public GenerateResponse<Map<String, Object>> getRecharge(GetRechargeParameter parameter) {
        int pageSize = parameter.getPageSize();
        int pageNumber = parameter.getPageNumber();

        LambdaQueryWrapper<Recharge> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Recharge::getUserId, this.util.getUserId());
        wrapper.orderByDesc(Recharge::getCreationTime);
        Page<Recharge> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);

        Page<Recharge> data = this.recharge.selectPage(page, wrapper);
        List<Map<String, Object>> results = data.getRecords().stream().map(item -> {
            Map<String, Object> result = new HashMap<>(Map.of(
                "id", item.getId(),
                "point", item.getPoint(),
                "money", item.getMoney(),
                "url", item.getUrl(),
                "state", item.isState(),
                "creationTime", Auxiliary.localDateTimeToTimestamp(item.getCreationTime())
            ));
            result.put("paymentTime", item.getPaymentTime() == null ? null : Auxiliary.localDateTimeToTimestamp(item.getPaymentTime()));
            return result;
        }).toList();
        return new GenerateResponse<>(Map.of(
            "data", results,
            "total", data.getTotal()
        ));
    }

    public GenerateResponse<Map<String, Object>> getRecord(GetRecordParameter parameter) {
        int toolId = parameter.getToolId();
        int pageSize = parameter.getPageSize();
        int pageNumber = parameter.getPageNumber();

        if (this.tool.selectById(toolId) == null) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "工具ID不存在");
        }

        LambdaQueryWrapper<Record> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Record::getToolId, toolId);
        wrapper.eq(Record::getUserId, this.util.getUserId());
        wrapper.orderByDesc(Record::getTime);
        Page<Record> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageNumber);

        Page<Record> data = this.record.selectPage(page, wrapper);
        List<Map<String, Object>> results = data.getRecords().stream().map(item -> {
            return Map.<String, Object>of(
                "id", item.getId(),
                "request", item.getRequest(),
                "response", item.getResponse(),
                "time", Auxiliary.localDateTimeToTimestamp(item.getTime())
            );
        }).toList();
        return new GenerateResponse<>(Map.of(
            "data", results,
            "total", data.getTotal()
        ));
    }

    public GenerateResponse<Integer> useCard(UseCardParameter parameter) {
        String code = parameter.getCode();

        LambdaQueryWrapper<Card> cardWrapper = new LambdaQueryWrapper<>();
        cardWrapper.eq(Card::getCode, code);
        Card card = this.card.selectOne(cardWrapper);
        if (card == null) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "码不存在");
        } else if (card.getCount() >= card.getNumber()) {
            throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "已被兑换完");
        }

        LambdaQueryWrapper<CardRecord> cardRecordWrapper = new LambdaQueryWrapper<>();
        cardRecordWrapper.eq(CardRecord::getCardId, card.getId());
        cardRecordWrapper.eq(CardRecord::getUserId, this.util.getUserId());
        if (this.cardRecord.exists(cardRecordWrapper)) {
            throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "已兑换");
        }

        CardRecord cardRecord = new CardRecord();
        cardRecord.setCardId(card.getId());
        cardRecord.setUserId(this.util.getUserId());
        this.cardRecord.insert(cardRecord);

        card.setCount(card.getCount() + 1);
        this.card.updateById(card);

        User user = this.user.selectById(this.util.getUserId());
        user.setRewardBalance(user.getRewardBalance() + card.getPoint());
        this.user.updateById(user);
        return new GenerateResponse<>(card.getPoint());
    }
}