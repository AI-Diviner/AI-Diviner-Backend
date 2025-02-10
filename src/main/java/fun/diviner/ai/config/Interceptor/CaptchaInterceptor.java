package fun.diviner.ai.config.Interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;

import fun.diviner.ai.redis.CaptchaRedis;
import fun.diviner.ai.util.response.ExceptionResponse;
import fun.diviner.ai.util.response.ExceptionResponseCode;

@Component
public class CaptchaInterceptor implements HandlerInterceptor {
    @Autowired
    private CaptchaRedis captcha;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String key = request.getParameter("captchaKey");
        String code = request.getParameter("captchaCode");
        if (key == null || key.isEmpty() || code == null || code.isEmpty()) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "验证码参数不能为空");
        }

        String correctCaptchaCode = this.captcha.get(key);
        if (correctCaptchaCode != null) {
            this.captcha.delete(key);
        }
        if (!code.equalsIgnoreCase(correctCaptchaCode)) {
            throw new ExceptionResponse(ExceptionResponseCode.PARAMETER_ERROR, "验证码错误");
        }
        return true;
    }
}