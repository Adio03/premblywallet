package org.springcorepractice.walletapplication.infrastructure.utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springcorepractice.walletapplication.domain.exceptions.IdentityManagerException;
import org.springcorepractice.walletapplication.domain.validator.IdentityValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
public class TokenUtils {
    @Value("${jwt_secret}")
    private String secret;

    @Value("${expiration}")
    private  long expiration;

    public  String generateToken(String email) throws IdentityManagerException {
        IdentityValidator.validatEmail(email);
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String decodeJWT(String token) throws IdentityManagerException {
           IdentityValidator.validateDataElement(token);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject();
        }
    }
