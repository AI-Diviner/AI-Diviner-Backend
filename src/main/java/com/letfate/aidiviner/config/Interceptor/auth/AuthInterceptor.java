package com.letfate.aidiviner.config.Interceptor.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import io.jsonwebtoken.Claims;

import com.letfate.aidiviner.redis.AccessTokenRedis;
import com.letfate.aidiviner.mapper.UserMapper;
import com.letfate.aidiviner.service.UtilService;
import com.letfate.aidiviner.util.response.ExceptionResponse;
import com.letfate.aidiviner.util.response.ExceptionResponseCode;
import com.letfate.aidiviner.entity.Special;
import com.letfate.aidiviner.entity.Auth;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private AccessTokenRedis accessToken;
    @Autowired
    private UserMapper user;
    @Autowired
    private UtilService util;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");
        if (token == null) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "Token不能为空");
        }

        Claims payload;
        token = token.replace("Bearer ", "");
        Integer id;
        try {
            payload = JWTUtil.parser(token);
            token = (String) payload.get("token");
            id = this.accessToken.get(token);
            if (id == null) {
                throw new Exception();
            } else if (this.user.selectById(id) == null) {
                this.accessToken.delete(token);
                throw new Exception();
            }
        } catch (Exception error) {
            throw new ExceptionResponse(ExceptionResponseCode.USER_ERROR, "Token错误");
        }

        Auth auth = new Auth();
        auth.setId(id);
        auth.setToken(token);
        this.util.setAttribute(Special.AttributeName.user, auth);
        return true;
    }
}