package com.expressbank.repository;

import com.expressbank.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<StockEntity, Integer> {
}
