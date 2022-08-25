package com.seb39.mystackoverflow.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.seb39.mystackoverflow.auth.PrincipalDetails;
import lombok.RequiredArgsConstructor;
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


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final String secret;
    private final long expirationTimeMillis;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principal = (PrincipalDetails) authResult.getPrincipal();
        String jwtToken = createJwtToken(principal);
        response.addHeader(HttpHeaders.AUTHORIZATION, getAuthorizationHeader(jwtToken));
    }

    private String createJwtToken(PrincipalDetails principal) {
        return JWT.create()
                .withSubject("login jwt token")
                .withExpiresAt(getExpiredDate())
                .withClaim("id", principal.getMemberId())
                .withClaim("username", principal.getUsername())
                .sign(Algorithm.HMAC512(secret));
    }

    private Date getExpiredDate(){
        return new Date(System.currentTimeMillis() + expirationTimeMillis);
    }

    private String getAuthorizationHeader(String token){
        return "Bearer "+token;
    }
}
