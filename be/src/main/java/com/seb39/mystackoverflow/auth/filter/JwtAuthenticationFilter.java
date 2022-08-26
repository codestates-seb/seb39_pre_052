package com.seb39.mystackoverflow.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.auth.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final String secret;
    private final long expirationTimeMillis;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest = null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            log.error("Failed to login request body deserialization", e);
            throw new RuntimeException(e);
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        log.info("AuthenticationFilter - attemptAuthentication. username={}, password={}", username, password);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        log.info("AuthenticationFilter - successfulAuthentication");

        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = createJwtToken(principal);
        response.addHeader(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(jwtToken));
    }

    private String createJwtToken(PrincipalDetails principal) {
        return JWT.create()
                .withSubject("login jwt token")
                .withExpiresAt(getExpiredDate())
                .withClaim("username", principal.getUsername())
                .sign(Algorithm.HMAC512(secret));
    }

    private Date getExpiredDate() {
        return new Date(System.currentTimeMillis() + expirationTimeMillis);
    }

    private String getAuthorizationHeader(String token) {
        return "Bearer " + token;
    }
}
