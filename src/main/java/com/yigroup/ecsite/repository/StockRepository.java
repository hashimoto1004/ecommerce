package com.yigroup.ecsite.repository;

import java.util.Optional;

import com.yigroup.ecsite.entity.Stock;

public interface StockRepository {
    Optional<Stock> findById(int id);
}
