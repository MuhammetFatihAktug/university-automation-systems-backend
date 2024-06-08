package org.dpu.collageautomationsystemsbackend.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.config.JwtService;
import org.dpu.collageautomationsystemsbackend.entities.auth.AuthenticationRequest;
import org.dpu.collageautomationsystemsbackend.entities.auth.AuthenticationResponse;
import org.dpu.collageautomationsystemsbackend.entities.auth.Token;
import org.dpu.collageautomationsystemsbackend.entities.student.Student;
import org.dpu.collageautomationsystemsbackend.enums.TokenType;
import org.dpu.collageautomationsystemsbackend.repository.StudentRepository;
import org.dpu.collageautomationsystemsbackend.repository.TokenRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtService jwtService;
    private final StudentRepository repository;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticateUser(request.getEmail(), request.getPassword());
        var user = findUserByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return buildAuthenticationResponse(jwtToken, refreshToken);
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        String refreshToken = authHeader.substring(7);
        String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = findUserByEmail(userEmail);
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = buildAuthenticationResponse(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void authenticateUser(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (Exception e) {
            throw new RuntimeException("Invalid email or password", e);
        }
    }

    private Student findUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    private void saveUserToken(Student user, String jwtToken) {
        var token = Token.builder()
                .student(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(Student user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getStudentNumber());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }

    private AuthenticationResponse buildAuthenticationResponse(String accessToken, String refreshToken) {
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
