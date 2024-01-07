package com.example.expandedproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

// @RequiredArgsConstructor
// @Component
public class JwtUtils {
    public static String generateAccessToken(String email, String nickname, Long id, String key) {
        /*
            param에 Long id도 받는다.
             - 추후 authentication.getPrincipal() 등에서 사용
             - member의 id값이 token에 있다면 수정, 조회 등에서 바로 사용할 수 있기 때문
        */

        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("nickname", nickname);
        // 로그인 후 상단에 nickname 뜨게 하기 위해서 token에 nickname도 넣는다.
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date((System.currentTimeMillis() + 3600000) * 1000))
                // 이 시간은 매우 긴 시간이므로 추후 실제 프로젝트 단계에서는
                // refresh token 등을 사용해서 token을 갱신하는 등의 방식으로 수정해야 할 것으로 보임.

                //.signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                // .signWith(SignatureAlgorithm.ES512, secretKey.getBytes())
                 .signWith(getSignKey(key), SignatureAlgorithm.HS256)
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        // return Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // 유저에게 발급된 jwt token이 유효한지 여부를 확인하는 메소드
    public static Boolean validate(String token, String key) {
        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        // return usernameByToken.equals(username) && !result;
        return !result;
    }

    public static String getMemberEmail(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);

    }

    public static Long getMemberId(String token, String key) {
        return extractAllClaims(token, key).get("id", Long.class);
    }

    public static String getMemberNickname(String token, String key) {
        return extractAllClaims(token, key).get("nickname", String.class);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
