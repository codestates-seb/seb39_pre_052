package com.seb39.mystackoverflow.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.auth.JwtManager;
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


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtManager jwtManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        LoginRequest loginRequest = getLoginRequest(request);

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(token);
    }

    private LoginRequest getLoginRequest(HttpServletRequest request) {
        LoginRequest loginRequest = null;
        try {
            loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
        } catch (IOException e) {
            log.error("Failed to login request body deserialization", e);
            throw new RuntimeException(e);
        }
        return loginRequest;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = jwtManager.createJwtToken(principal.getUsername());
        response.addHeader(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(jwtToken));
    }
    private String getAuthorizationHeader(String token) {
        return "Bearer " + token;
    }
}
