package com.example.expandedproject.cart.repository;


import com.example.expandedproject.cart.model.Cart;
import com.example.expandedproject.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByMember(Member member);
    void deleteByIdAndMember(Long id, Member member);
}
