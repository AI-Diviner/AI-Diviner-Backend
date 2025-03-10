package fun.diviner.aidiviner.config.Interceptor.auth;

import javax.crypto.SecretKey;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.Date;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import fun.diviner.aidiviner.entity.Special;

public class JWTUtil {
    private static SecretKey key = Keys.hmacShaKeyFor(Special.authSecret.getBytes());

    public static <T> String generate(Map<String, T> payload, int time, TimeUnit unit) {
        Date expiration = new Date(new Date().getTime() + unit.toMillis(time));
        return Jwts.builder().claims(payload).expiration(expiration).signWith(JWTUtil.key).compact();
    }

    public static Claims parser(String token) {
        return Jwts.parser().verifyWith(JWTUtil.key).build().parseSignedClaims(token).getPayload();
    }
}