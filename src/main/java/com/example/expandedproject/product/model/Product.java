package com.example.expandedproject.product.model;

import com.example.expandedproject.cart.model.Cart;
import com.example.expandedproject.product.model.request.PostCreateProductDtoReq;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 300, nullable = false)
    private String content;

    @Min(0)
    @Column(nullable = false)
    private Integer price;

    @Column(length = 200)
    private String img;

    @Column(length = 100, nullable = false, unique = true)
    private String address;

    @Column(length = 50, nullable = false)
    private String latitude;

    @Column(length = 50, nullable = false)
    private String longitude;

    @Min(1)
    private Integer maxUser;

    @ColumnDefault("1")
    @Comment("0: 미보유 | 1: 보유")
    private Boolean hasWashingMachine;

    @ColumnDefault("1")
    @Comment("0: 미보유 | 1: 보유")
    private Boolean hasAirConditioner;

    @ColumnDefault("1")
    @Comment("0: 비활성화 | 1: 활성화")
    private Boolean isActive;

    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY)
    private List<Cart> Carts = new ArrayList<>();

    @Column(updatable = false, nullable = false)
    private Date createdAt;

    private Date updatedAt;

    @PrePersist
    void createdAt() {
        this.createdAt = Timestamp.from(Instant.now());
        this.updatedAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static Product toEntity(PostCreateProductDtoReq request) {
        return Product.builder()
                .name(request.getName())
                .content(request.getContent())
                .price(request.getPrice())
                .address(request.getAddress())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .maxUser(request.getMaxUser())
                .hasAirConditioner(request.getHasAirConditioner())
                .hasWashingMachine(request.getHasWashingMachine())
                .build();
    }
}
