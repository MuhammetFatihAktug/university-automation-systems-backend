package org.dpu.collageautomationsystemsbackend.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.dpu.collageautomationsystemsbackend.dto.StudentDTO;
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

    public String createToken(StudentDTO studentDTO) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 10000);
        return JWT.create()
                .withIssuer(studentDTO.studentNumber().toString())
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .withClaim("tc", studentDTO.tc())
                .withClaim("firstName", studentDTO.firstName())
                .withClaim("lastName", studentDTO.lastName())
                .withClaim("phoneNumber", studentDTO.phoneNumber())
                .withClaim("birthDate", studentDTO.birthDate().getTime())
                .withClaim("gender", String.valueOf(studentDTO.gender()))
                .withClaim("address", studentDTO.address())
                .withClaim("grade", studentDTO.grade())
                .withClaim("registrationDate", studentDTO.registrationDate().getTime())
                .withClaim("curriculum", studentDTO.curriculum())
                .withClaim("studyDurationStatus", studentDTO.studyDurationStatus())
                .withClaim("tuitionStatus", studentDTO.tuitionStatus())
                .withClaim("password", studentDTO.password())
                .sign(Algorithm.HMAC256(secretKey));

    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);

        StudentDTO student = new StudentDTO(
                Long.parseLong(decodedJWT.getIssuer()),
                decodedJWT.getClaim("tc").asLong(),
                decodedJWT.getClaim("firstName").asString(),
                decodedJWT.getClaim("lastName").asString(),
                decodedJWT.getClaim("phoneNumber").asString(),
                new Date(decodedJWT.getClaim("birthDate").asLong()),
                decodedJWT.getClaim("gender").asString().charAt(0),
                decodedJWT.getClaim("address").asString(),
                decodedJWT.getClaim("grade").asInt(),
                new Date(decodedJWT.getClaim("registrationDate").asLong()),
                decodedJWT.getClaim("curriculum").asString(),
                decodedJWT.getClaim("studyDurationStatus").asString(),
                decodedJWT.getClaim("tuitionStatus").asString(),
                decodedJWT.getClaim("password").asString()
        );

        return new UsernamePasswordAuthenticationToken(student, null, Collections.emptyList());
    }

}
