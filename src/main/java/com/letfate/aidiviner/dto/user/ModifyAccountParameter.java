package com.letfate.aidiviner.dto.user;

import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class ModifyAccountParameter {
    @Pattern(regexp="[A-Za-z0-9]{5,15}$", message="密码仅能为5~15长度的数字和字母")
    private String password;

    public void setPassword(String password) {
        this.password = password.isEmpty() ? null : password;
    }
}