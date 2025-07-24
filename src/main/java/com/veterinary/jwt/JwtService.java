package com.veterinary.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtService {

    public static final String SECRET_KEY = "fywp0KlK6+HIr91gH9M6PR7JKTcfIt+e5NDaDyvC88g=";

    public String generateToken(UserDetails user) {

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Key getKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token).getBody();
    }

    public Object getClaimsByKey(String token, String key) {
        Claims claims = getClaims(token);
        return claims.get(key);
    }

    public <T> T exportToken(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = getClaims(token);
        return claimsTFunction.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date expiredDate = exportToken(token, Claims::getExpiration);
        return new Date().before(expiredDate);
    }

    public String getUserNameByToken(String token) {
        return exportToken(token, Claims::getSubject);
    }
}
