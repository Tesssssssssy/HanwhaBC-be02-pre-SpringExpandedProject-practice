package com.example.expandedproject.product.repository.querydsl;

import com.example.expandedproject.image.model.QImage;
import com.example.expandedproject.member.model.QMember;
import com.example.expandedproject.product.model.Product;
import com.example.expandedproject.product.model.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {
    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findList() {
        // JOIN해야 하는 것들 생성.
        QProduct product = new QProduct("product");
        QMember member = new QMember("member");
        QImage image = new QImage("image");

        List<Product> result = from(product)
                .leftJoin(product.imageList, image).fetchJoin()
                .leftJoin(product.brandIdx, member).fetchJoin()
                .fetch().stream().distinct().collect(Collectors.toList());

        return result;
    }

    @Override
    public Page<Product> findListUsingPaging(Pageable pageable) {
        QProduct product = new QProduct("product");
        QMember member = new QMember("member");
        QImage image = new QImage("image");

        List<Product> result = from(product)
                .leftJoin(product.imageList, image).fetchJoin()
                .leftJoin(product.brandIdx, member).fetchJoin()
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                //.fetch().stream().distinct().collect(Collectors.toList());
                .distinct()
                .fetch().stream().collect(Collectors.toList());

        return new PageImpl<>(result, pageable, result.size());
    }
}
