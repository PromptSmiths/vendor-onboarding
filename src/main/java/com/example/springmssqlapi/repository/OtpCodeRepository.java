package com.example.springmssqlapi.repository;

import com.example.springmssqlapi.entity.OtpCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {
    
    Optional<OtpCode> findByEmailAndOtpCodeAndIsUsedFalse(String email, String otpCode);
    
    Optional<OtpCode> findTopByEmailAndIsUsedFalseOrderByCreatedAtDesc(String email);
    
    @Modifying
    @Query("DELETE FROM OtpCode o WHERE o.expiresAt < :currentTime")
    void deleteExpiredOtps(@Param("currentTime") LocalDateTime currentTime);
    
    @Modifying
    @Query("UPDATE OtpCode o SET o.isUsed = true WHERE o.email = :email AND o.isUsed = false")
    void markAllOtpsAsUsedForEmail(@Param("email") String email);
}