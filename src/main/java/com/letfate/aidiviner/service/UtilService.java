package com.letfate.aidiviner.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.letfate.aidiviner.mapper.CoreMapper;
import com.letfate.aidiviner.mapper.UserMapper;
import com.letfate.aidiviner.mapper.RecordMapper;
import com.letfate.aidiviner.entity.Auth;
import com.letfate.aidiviner.entity.Special;
import com.letfate.aidiviner.entity.PayType;
import com.letfate.aidiviner.entity.Pay;
import com.letfate.aidiviner.entity.Trade;
import com.letfate.aidiviner.entity.database.Core;
import com.letfate.aidiviner.entity.database.User;
import com.letfate.aidiviner.entity.database.Record;

@Service
public class UtilService {
    @Autowired
    private CoreMapper core;
    @Autowired
    private UserMapper user;
    @Autowired
    private RecordMapper record;

    public <T> void setAttribute(String key, T value) {
        RequestContextHolder.currentRequestAttributes().setAttribute(key, value, ServletRequestAttributes.SCOPE_REQUEST);
    }

    public <T> T getAttribute(String key) {
        return (T) RequestContextHolder.currentRequestAttributes().getAttribute(key, ServletRequestAttributes.SCOPE_REQUEST);
    }

    public int getUserId() {
        return ((Auth) this.getAttribute(Special.AttributeName.user)).getId();
    }

    public String getCoreContent(String name) {
        LambdaQueryWrapper<Core> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Core::getName, name);
        return this.core.selectOne(wrapper).getContent();
    }

    public Pay pay(PayType type, int price) {
        Pay pay = new Pay();
        if (price == 0) {
            pay.setType(PayType.FREE);
        } else {
            pay.setType(type);
            User user = this.user.selectById(this.getUserId());
            switch (type) {
                case RECHARGE_BALANCE -> {
                    if (price > user.getRechargeBalance()) {
                        return pay;
                    }

                    pay.setRecharge(price);
                    user.setRechargeBalance(user.getRechargeBalance() - price);
                }
                case BALANCE -> {
                    if (price <= user.getRewardBalance()) {
                        pay.setReward(price);
                        user.setRewardBalance(user.getRewardBalance() - price);
                    } else {
                        int miss = price - user.getRewardBalance();
                        if (miss > user.getRechargeBalance()) {
                            return pay;
                        }

                        pay.setReward(user.getRewardBalance());
                        pay.setRecharge(miss);
                        user.setRewardBalance(0);
                        user.setRechargeBalance(user.getRechargeBalance() - miss);
                    }
                }
            }
            this.user.updateById(user);
        }
        pay.setState(true);
        return pay;
    }

    public void refund() {
        Pay pay = ((Trade) this.getAttribute(Special.AttributeName.trade)).getPay();
        if (pay.getType() == PayType.FREE) {
            return;
        }

        User user = this.user.selectById(this.getUserId());
        switch (pay.getType()) {
            case RECHARGE_BALANCE -> {
                user.setRechargeBalance(user.getRechargeBalance() + pay.getRecharge());
            }
            case BALANCE -> {
                user.setRewardBalance(user.getRewardBalance() + pay.getReward());
                user.setRechargeBalance(user.getRechargeBalance() + pay.getRecharge());
            }
        }
        this.user.updateById(user);
    }

    public void record(String request, String response) {
        Record record = new Record();
        record.setId(UUID.randomUUID().toString());
        record.setToolId(((Trade) this.getAttribute(Special.AttributeName.trade)).getId());
        record.setRequest(request);
        record.setResponse(response);
        record.setUserId(this.getUserId());
        this.record.insert(record);
    }
}