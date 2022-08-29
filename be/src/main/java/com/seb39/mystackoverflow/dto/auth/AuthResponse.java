package com.seb39.mystackoverflow.dto.auth;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private boolean isSuccess;
    private String failureReason;

    private AuthResponse(boolean isSuccess, String failureReason) {
        this.isSuccess = isSuccess;
        this.failureReason = failureReason;
    }

    public static AuthResponse success(){
        return new AuthResponse(true, "");
    }

    public static AuthResponse failure(String reason){
        return new AuthResponse(false,reason);
    }
}
