package com.example.expandedproject.cart.model;

import com.example.expandedproject.member.model.Member;
import com.example.expandedproject.product.model.Product;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Builder
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "Member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "Product_id")
    private Product product;

    Integer amount;
}
