package com.veterinary.business;

import com.veterinary.dao.RefreshTokenRepository;
import com.veterinary.dto.request.RefreshTokenRequest;
import com.veterinary.dto.response.AuthResponse;
import com.veterinary.entities.RefreshToken;
import com.veterinary.jwt.JwtService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final JwtService jwtService;
    private final AuthService authService;

    public RefreshTokenService(RefreshTokenRepository repository, JwtService jwtService, AuthService authService) {
        this.repository = repository;
        this.jwtService = jwtService;
        this.authService = authService;
    }

    public boolean isExpiredRefreshToken(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> token = repository.findByRefreshToken(request.getRefreshToken());
        if (token.isEmpty()) {
            System.out.println("Refresh token geçersizdir" + request.getRefreshToken());
        }

        RefreshToken refreshToken = token.get();
        if (!isExpiredRefreshToken(refreshToken.getExpireDate())) {
            System.out.println("Refresh token expire olmuştur.");
        }

        String accessToken = jwtService.generateToken(refreshToken.getUser());

        RefreshToken newRefreshToken = repository.save(authService.createRefreshToken(refreshToken.getUser()));

        return new AuthResponse(accessToken,newRefreshToken.getRefreshToken());
    }
}
