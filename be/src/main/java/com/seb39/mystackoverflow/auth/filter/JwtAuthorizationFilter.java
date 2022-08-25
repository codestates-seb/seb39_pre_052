package com.seb39.mystackoverflow.auth.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.entity.Member;
import com.seb39.mystackoverflow.service.MemberService;
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

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final MemberService memberService;
    private final String secret;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberService memberService, String secret) {
        super(authenticationManager);
        this.memberService = memberService;
        this.secret = secret;
    }

    /**
     * Token 검증
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isJwtTokenHeader(header)) {
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = getJwtToken(header);
        Long memberId = decodeJwtTokenAndGetMemberId(jwtToken);

        if(memberId != null){
            Member member = memberService.findById(memberId);
            PrincipalDetails principal = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request,response);
        }

        super.doFilterInternal(request, response, chain);
    }

    private boolean isJwtTokenHeader(String header) {
        return StringUtils.hasText(header) && header.startsWith("Bearer");
    }

    private String getJwtToken(String header){
        return header.replace("Bearer ","");
    }

    private Long decodeJwtTokenAndGetMemberId(String jwtToken){
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(jwtToken)
                .getClaim("id")
                .asLong();
    }
}
