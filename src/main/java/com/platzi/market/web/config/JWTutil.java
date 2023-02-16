package com.platzi.market.web.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class JWTutil {
    private static final String KEY = "D4ni3l";
    public String generateToken(UserDetails userDetails){
        return Jwts.builder().setSubject(userDetails.getUsername()).setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, KEY).compact();
    }

    public boolean validateToken(String token, UserDetails userDetails){
        return  userDetails.getUsername().equals(extractUserName(token)) && isTokenExpired(token);
    }
    public String extractUserName(String token){
        return getClaims(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        return getClaims(token).getExpiration().after(new Date()); // la capacitacion tenia before pero la respuesta era FAlSE, se cambia por after
    }
    private Claims getClaims(String token){
        return  Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
    }
}
