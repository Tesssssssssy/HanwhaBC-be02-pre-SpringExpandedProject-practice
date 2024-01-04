package com.example.expandedproject.product.repository;

import com.example.expandedproject.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Optional<Product> findByName(String name);
}
