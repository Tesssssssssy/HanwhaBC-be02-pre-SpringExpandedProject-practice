package com.example.expandedproject.cart.repository;

import com.example.expandedproject.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByMemberId(Long memberId);
}
