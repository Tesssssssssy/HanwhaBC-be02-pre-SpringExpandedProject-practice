package com.example.expandedproject.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class JwtUtils {
    private final UserDetailsService userDetailsService;
    public static String generateAccessToken(String email, String authority, String nickname) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("authority", authority);
        claims.put("nickname", nickname);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setSubject(authority)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 180000))
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS512))
                .compact();

        return token;
    }

    public static Key getSignKey(String secretKey) {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public static Boolean validate(String token, String username, String key) {
        String usernameByToken = getUsername(token, key);

        Date expireTime = extractAllClaims(token, key).getExpiration();
        Boolean result = expireTime.before(new Date(System.currentTimeMillis()));

        return usernameByToken.equals(username) && !result;
    }

    public static String getUsername(String token, String key) {
        return extractAllClaims(token, key).get("email", String.class);

    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
