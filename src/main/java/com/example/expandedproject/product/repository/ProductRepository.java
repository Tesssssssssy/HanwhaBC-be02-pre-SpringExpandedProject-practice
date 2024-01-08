package com.example.expandedproject.product.repository;

import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.repository.querydsl.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByName(String name);

    @Lock(LockModeType.OPTIMISTIC)
    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Product> findById(Long id);

    // JPQL 방식
    @Query("select p from Product as p LEFT JOIN p.brandIdx JOIN FETCH p.brandIdx")
    List<Product> findAll();


    // JPQL도 아니고 그냥 SQL
    @Query(nativeQuery = true, value =
            "SELECT p " +
            "from Product p " +
            "LEFT JOIN Image i " +
            "ON p.id = i.product_id LEFT JOIN Member m ON p.member_id = m.id " +
            "LIMIT :page, :size")
    List<Product> findAllQueryWithPage(Integer page, Integer size);
}
