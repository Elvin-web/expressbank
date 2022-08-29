package com.expressbank.repository;

import com.expressbank.entity.OrderEntity;
import com.expressbank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRespository extends JpaRepository<OrderEntity, Integer> {

    List<OrderEntity> findAllByUserEntityOrderByCreatedDateDesc(UserEntity user);
}
