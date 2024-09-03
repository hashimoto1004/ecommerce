package com.yigroup.ecsite.repository;

import com.yigroup.ecsite.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> findAll();

    Optional<Product> findById(int id);

    void save(Product product);

    void deleteById(int id);
}
