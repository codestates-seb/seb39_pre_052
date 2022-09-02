package com.seb39.mystackoverflow.controller;

import com.seb39.mystackoverflow.auth.PrincipalDetails;
import com.seb39.mystackoverflow.dto.auth.SignUpRequest;
import com.seb39.mystackoverflow.dto.auth.AuthResponse;
import com.seb39.mystackoverflow.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signUp(@Valid @RequestBody SignUpRequest signUpRequest){
        memberService.signUp(signUpRequest.toMember());
        AuthResponse response = AuthResponse.success();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/auth/my")
    @Secured("ROLE_USER")
    public String authTest(@AuthenticationPrincipal PrincipalDetails principal){
        return principal.getUsername();
    }

    /**
     * TODO : 전역 에러 처리 핸들러로 옮기기 (@ControllerAdvice)
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AuthResponse> errorHandler(IllegalArgumentException e){
        String reason = e.getMessage();
        AuthResponse response = AuthResponse.failure(reason);
        return new ResponseEntity<>(response,HttpStatus.CONFLICT);
    }
}