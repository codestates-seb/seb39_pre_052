package com.seb39.mystackoverflow.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtManager {

    @Value("${app.auth.jwt.secret}")
    private String secret;

    @Value("${app.auth.jwt.expiration-time-millis}")
    private long expirationTimeMillis;

    public String createJwtToken(String email) {
        return JWT.create()
                .withSubject("login jwt token")
                .withExpiresAt(getExpiredDate())
                .withClaim("email", email)
                .sign(Algorithm.HMAC512(secret));
    }

    private Date getExpiredDate() {
        return new Date(System.currentTimeMillis() + expirationTimeMillis);
    }

    public String decodeJwtTokenAndGetEmail(String jwtToken){
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(jwtToken)
                .getClaim("email")
                .asString();
    }
}
