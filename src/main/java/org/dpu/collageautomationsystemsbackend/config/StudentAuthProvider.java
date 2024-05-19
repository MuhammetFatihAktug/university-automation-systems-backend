package org.dpu.collageautomationsystemsbackend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.student.StudentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;


@RequiredArgsConstructor
@Component
public class StudentAuthProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(StudentDto studentDto) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 10000);
        return JWT.create()
                .withIssuer(studentDto.getStudentNumber())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("studentName", studentDto.getName())
                .withClaim("studentLastName", studentDto.getLastName())
                .sign(Algorithm.HMAC256(secretKey));
 
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        StudentDto student = StudentDto.builder()
                .studentNumber(decodedJWT.getIssuer())
                .name(decodedJWT.getClaim("studentName").asString())
                .lastName(decodedJWT.getClaim("studentLastName").asString())
                .build();

        return new UsernamePasswordAuthenticationToken(student, null, Collections.emptyList());
    }

}
