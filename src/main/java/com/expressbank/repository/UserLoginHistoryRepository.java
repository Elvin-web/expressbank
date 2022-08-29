package com.expressbank.repository;

import com.expressbank.entity.UserLoginHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface UserLoginHistoryRepository extends JpaRepository<UserLoginHistoryEntity, Long> {

    UserLoginHistoryEntity findByRefreshTokenAndActive(String refreshToken, Integer active);

    @Modifying
    @Query(value = "update UserLoginHistoryEntity set active = 0 where active = 1 and user.id =:userId")
    void doDeactive(@Param("userId") Integer userId);

    UserLoginHistoryEntity findByUser_IdAndActive(Integer userId, Integer active);
}
