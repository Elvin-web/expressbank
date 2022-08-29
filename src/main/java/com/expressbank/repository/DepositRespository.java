package com.expressbank.repository;

import com.expressbank.entity.DepositEntity;
import com.expressbank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositRespository extends JpaRepository<DepositEntity, Integer> {

    Optional<DepositEntity> findDepositEntityByIban(String iban);

    Optional<DepositEntity> findDepositEntityByUserEntity(UserEntity userEntity);
}
