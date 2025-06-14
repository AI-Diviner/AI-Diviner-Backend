package com.letfate.aidiviner.dto.user;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import lombok.Data;

@Data
public class RegisterLoginParameter {
    @NotEmpty(message="手机号不能为空")
    @Pattern(regexp="^1[3-9]\\d{9}$", message="手机号仅能为中国大陆的手机号")
    private String phoneNumber;

    @NotEmpty(message="密码不能为空")
    @Pattern(regexp="[A-Za-z0-9]{5,15}$", message="密码仅能为5~15长度的数字和字母")
    private String password;
}