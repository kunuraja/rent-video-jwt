package com.raj.rentvideo.service;

import com.raj.rentvideo.entity.UserEntity;
import com.raj.rentvideo.enums.RoleEnum;
import com.raj.rentvideo.exchange.AuthRequest;
import com.raj.rentvideo.exchange.AuthResponse;
import com.raj.rentvideo.exchange.RegisterRequest;
import com.raj.rentvideo.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserEntityRepository userEntityRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (request.getRole() == null) {
            request.setRole(RoleEnum.CUSTOMER);
        }

        UserEntity userEntity = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userEntityRepository.save(userEntity);

        return AuthResponse.builder().build();

    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(), request.getPassword()));
        return AuthResponse.builder().build();
    }

}
