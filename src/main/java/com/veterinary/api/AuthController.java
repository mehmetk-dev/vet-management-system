package com.veterinary.api;

import com.veterinary.business.AuthService;
import com.veterinary.business.RefreshTokenService;
import com.veterinary.dto.request.RefreshTokenRequest;
import com.veterinary.dto.request.UserRequest;
import com.veterinary.dto.response.AuthResponse;
import com.veterinary.dto.response.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserRequest request) {
        return authService.register(request);
    }

    @PostMapping("/authenticate")
    public AuthResponse authenticate(@RequestBody UserRequest request) {
        return authService.authenticate(request);
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request) {
        return refreshTokenService.refreshToken(request);
    }
}
