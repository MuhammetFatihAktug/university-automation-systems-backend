package org.dpu.collageautomationsystemsbackend.repository;

import org.dpu.collageautomationsystemsbackend.entities.auth.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            select t from Token t inner join Student u\s
            on t.student.studentNumber = u.studentNumber\s
            where u.studentNumber = :id and (t.expired = false or t.revoked = false)\s
            """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}