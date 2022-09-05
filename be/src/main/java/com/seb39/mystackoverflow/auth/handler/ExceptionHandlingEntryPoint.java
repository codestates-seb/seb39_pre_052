package com.seb39.mystackoverflow.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seb39.mystackoverflow.dto.auth.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT 토큰에 문제가 있을 때 처리하는 EntryPoint
 */
@Slf4j
public class ExceptionHandlingEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.error("Authentication exception", authException);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        String exceptionReason = (String) request.getAttribute("exception");
        AuthResponse authResponse = AuthResponse.failure(exceptionReason);
        objectMapper.writeValue(response.getWriter(), authResponse);
    }
}
