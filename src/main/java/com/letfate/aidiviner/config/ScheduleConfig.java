package com.letfate.aidiviner.config;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;

import com.letfate.aidiviner.mapper.UserMapper;
import com.letfate.aidiviner.entity.database.User;
import com.letfate.aidiviner.entity.Special;

@Component
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    private UserMapper user;

    @Scheduled(cron="0 0 0 * * *")
    private void resetUser() {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(User::isCheckIn, true);
        wrapper.set(User::isCheckIn, false);
        this.user.update(null, wrapper);
    }

    @Scheduled(cron="0 */10 * * * *")
    private void checkUser() {
        LambdaUpdateWrapper<User> wrapper = new LambdaUpdateWrapper<>();
        wrapper.le(User::getGroupExpirationTime, LocalDateTime.now());
        wrapper.set(User::getGroupId, Special.commonGroupId);
        wrapper.set(User::getGroupExpirationTime, null);
        this.user.update(null, wrapper);
    }
}