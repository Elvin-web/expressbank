package com.expressbank.repository;

import com.expressbank.entity.ConfirmationTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationTokenEntity, Integer> {

    Optional<ConfirmationTokenEntity> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationTokenEntity c SET c.confirmedAt = :confirmedAt WHERE c.token = :token")
    int updateConfirmedAt(@Param("token") String token, @Param("confirmedAt") LocalDateTime confirmedAt);
}
