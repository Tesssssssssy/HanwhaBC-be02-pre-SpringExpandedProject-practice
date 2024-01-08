package com.example.expandedproject.product.model;

import com.example.expandedproject.image.model.Image;
import com.example.expandedproject.member.model.Member;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String name;

    @Column(length = 50)
    private Integer categoryIdx;

    @Column(length = 50, nullable = false, unique = true)
    private Integer price;

    private Integer salePrice;
    private String deliveryType;
    private String isTodayDeal;

    @Column(length = 200, nullable = false, unique = true)
    private String contents;

    @Version
    private Integer likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Member_id")
    private Member brandIdx;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Image> imageList = new ArrayList<>();


    public void increaseLikeCount() {
        this.likeCount += 1;
    }
}
