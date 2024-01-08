package com.example.expandedproject.product.repository.querydsl;

import com.example.expandedproject.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findList();

    Page<Product> findListUsingPaging(Pageable pageable);
}
