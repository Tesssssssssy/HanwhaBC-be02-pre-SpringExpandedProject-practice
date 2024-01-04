package com.example.expandedproject.member.repository;

import com.example.expandedproject.member.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    @Override
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
}
