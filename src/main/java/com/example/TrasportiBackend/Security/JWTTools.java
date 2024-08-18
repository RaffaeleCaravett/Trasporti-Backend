package com.example.TrasportiBackend.Security;

import com.example.TrasportiBackend.User.*;
import com.example.TrasportiBackend.exceptions.UnauthorizedException;
import com.example.TrasportiBackend.payloads.entities.Tokens;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    @Autowired
    TrasporatoreRepository trasportatoreRepository;
    @Autowired
    AziendaRepository aziendaRepository;
    @Autowired
    AdminRepository adminRepository;

    public Tokens createTokens(User user){
        String accessToken = Jwts.builder().setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*45))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

        String refreshToken = Jwts.builder().setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60 *24 *7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

        Tokens token = new Tokens(accessToken,refreshToken);
        return token;
    }
    public Trasportatore verifyTrasportatoreToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            return trasportatoreRepository.findById(Long.valueOf(userId)).get();
        }catch (Exception e){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login o refresha la pagina!");
        }
    }

    public Azienda verifyAziendaToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            return aziendaRepository.findById(Long.parseLong(userId)).get();
        }catch (Exception e){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }
    }

    public Admin verifyAdminToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            return adminRepository.findById(Long.parseLong(userId)).get();
        }catch (Exception e){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }
    }

    public Tokens verifyTrasportatoreRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId= claims.getSubject();

            return  this.createTokens(trasportatoreRepository.findById(Long.valueOf(userId)).get());
        }catch (Exception e){
            throw new UnauthorizedException("Il refresh token non è valido. Accedi nuovamente.");
        }
    }
    public Tokens verifyAziendaRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId= claims.getSubject();

            return  this.createTokens(aziendaRepository.findById(Long.valueOf(userId)).get());
        }catch (Exception e){
            throw new UnauthorizedException("Il refresh token non è valido. Accedi nuovamente.");
        }
    }
    public Tokens verifyAdminRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId= claims.getSubject();

            return  this.createTokens(adminRepository.findById(Long.valueOf(userId)).get());
        }catch (Exception e){
            throw new UnauthorizedException("Il refresh token non è valido. Accedi nuovamente.");
        }
    }

    public String extractIdFromToken(String token){
        return  Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
    }
}
