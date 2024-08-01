package com.LearnTools.LearnToolsApi.services;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.LearnTools.LearnToolsApi.model.entidades.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    @Value("${token.siging.key}")
    private String jwSingingKey;

    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String getLoginMethod(String jwt) {
        Claims claims = extractAllClaims(jwt);
        return claims.get("loginMethod", String.class);
    }

    public String generateToken(User user) {
        return generateToken(new HashMap<>(), user);
    }

    public boolean isTokenValid(String token, User user) {
        final String username = extractUserName(token);
        String authenticateUser = user.getUsername();

        return (username.equals(authenticateUser)) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token) {
        final String username = extractUserName(token);
        User user = (User) userService.userDetailsService().loadUserByUsername(username);
        String authenticateUser = user.getUsername();
        return (username.equals(authenticateUser)) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, User user) {
        String username = user.getUsername();
        LocalDateTime date = LocalDateTime.now().plusDays(1);
        return Jwts.builder().setClaims(extraClaims).setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getSiginingKey(), SignatureAlgorithm.HS256).compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSiginingKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSiginingKey() {
        byte[] KeyBytes = Decoders.BASE64.decode(jwSingingKey);
        return Keys.hmacShaKeyFor(KeyBytes);
    }

}
