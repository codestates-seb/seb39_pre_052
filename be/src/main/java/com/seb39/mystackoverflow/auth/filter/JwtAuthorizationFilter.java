package com.seb39.mystackoverflow.auth.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.seb39.mystackoverflow.auth.JwtUtils;
import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberService memberService;
    private final JwtUtils jwtUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService, JwtUtils jwtUtils) {
        super(authenticationManager);
        this.memberService = memberService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        log.info("AuthorizationFilter - doFilterInternal");

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isJwtTokenHeader(header)) {
            chain.doFilter(request,response);
            return;
        }

        String email = null;
        try{
            String jwtToken = getJwtToken(header);
            email = jwtUtils.decodeJwtTokenAndGetEmail(jwtToken);
        }catch(JWTVerificationException e){
            log.error("Invalid Jwt Token",e);
            request.setAttribute("exception","Invalid JWT Token");
        }

        if(StringUtils.hasText(email) && memberService.exist(email)){
            Member member = memberService.findByEmail(email);
            PrincipalDetails principal = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
            return;
        }

        super.doFilterInternal(request, response, chain);
    }


    private boolean isJwtTokenHeader(String header) {
        return StringUtils.hasText(header) && header.startsWith("Bearer");
    }

    private String getJwtToken(String header){
        return header.replace("Bearer ","");
    }


}
