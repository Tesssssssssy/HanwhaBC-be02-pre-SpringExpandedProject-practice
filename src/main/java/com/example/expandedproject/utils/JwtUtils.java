package com.example.expandedproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

// @RequiredArgsConstructor
// @Component
public class JwtUtils {
    public static String generateAccessToken(String username, String nickname, Long id, String key) {
        // param에 Long id도 받는다.
        // 그리고 token 생성하는 2곳에서 수정한다.
        // Authentication에서도 id값을 login할 때 ((Member) authentication.getPrincipal().getId();
        // 그리고 토큰 생성할 때 id값도 넣어준다.

        Claims claims = Jwts.claims();
        // claims.put("email", email);
        claims.put("username", username);
        claims.put("nickname", nickname);
        claims.put("id", id);

        String token = Jwts.builder()
                .setClaims(claims)
                //.setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (1 * 60 * 60 * 1000) * 1000))
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
        String usernameByToken = getMemberEmail(token, key);

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
