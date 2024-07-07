package pl.dgorecki.shop_scrapper.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tracked_product")
public class TrackedProduct {

    @Id
    @Column(name = "tracked_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_url")
    private String url;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "product_name")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Instant created;



}
