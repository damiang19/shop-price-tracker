package pl.dgorecki.pricetracker.entity;

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

    @Column(name = "shop_name")
    private String shopName;

    private Instant created;



}
