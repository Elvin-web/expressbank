package com.expressbank.repository;

import com.expressbank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUsernameIgnoreCase(String username);

    Optional<UserEntity> findUserByEmail(String email);


    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.accountVerified = true WHERE u.email = :email")
    int accountVerifiedUser(@Param("email") String email);
}
