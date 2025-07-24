package com.veterinary.business;

import com.veterinary.dao.RefreshTokenRepository;
import com.veterinary.dao.UserRepository;
import com.veterinary.dto.request.UserRequest;
import com.veterinary.dto.response.AuthResponse;
import com.veterinary.dto.response.UserResponse;
import com.veterinary.entities.RefreshToken;
import com.veterinary.entities.User;
import com.veterinary.jwt.JwtService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationProvider provider;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRepository userRepository,
                       AuthenticationProvider provider,
                       JwtService jwtService,
                       RefreshTokenRepository refreshTokenRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.provider = provider;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public AuthResponse authenticate(UserRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            provider.authenticate(authenticationToken);

            Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
            String accessToken = jwtService.generateToken(optionalUser.get());
            RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(optionalUser.get()));

            return new AuthResponse(accessToken,refreshToken.getRefreshToken());
        } catch (Exception e) {
            System.out.println("şifre yanlış");
        }
        return null;
    }
    public UserResponse register(UserRequest request){
        UserResponse userResponse = new UserResponse();
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser,userResponse);
        return userResponse;
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken =  new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 4));
        return refreshToken;
    }
}
