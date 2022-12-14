package com.seb39.mystackoverflow.auth;


import com.seb39.mystackoverflow.auth.handler.ExceptionHandlingEntryPoint;
import com.seb39.mystackoverflow.auth.filter.JwtAuthenticationFilter;
import com.seb39.mystackoverflow.auth.filter.JwtAuthorizationFilter;
import com.seb39.mystackoverflow.auth.handler.JwtAuthenticationFailureHandler;
import com.seb39.mystackoverflow.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final MemberService memberService;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final JwtManager jwtManager;


    @Value("${app.auth.jwt.secret}")
    private String secret;

    @Value("${app.auth.jwt.expiration-time-millis}")
    private long expirationTimeMillis;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.headers().frameOptions().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .apply(new CustomJwtConfigurer())
                .and()
                .authorizeRequests().anyRequest().permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new ExceptionHandlingEntryPoint());


        return http.build();
    }

    public class CustomJwtConfigurer extends AbstractHttpConfigurer<CustomJwtConfigurer, HttpSecurity>{

        @Override
        public void configure(HttpSecurity builder) throws Exception{
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtManager);
            jwtAuthenticationFilter.setAuthenticationFailureHandler(jwtAuthenticationFailureHandler);
            builder
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(new JwtAuthorizationFilter(authenticationManager,memberService, jwtManager));
        }
    }
}
