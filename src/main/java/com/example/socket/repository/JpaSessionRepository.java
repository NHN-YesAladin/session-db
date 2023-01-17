package com.example.socket.repository;

import com.example.socket.entity.SessionEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSessionRepository extends JpaRepository<SessionEntity, String> {
    Optional<SessionEntity> findById(String id);
    void deleteById(String id);
}
