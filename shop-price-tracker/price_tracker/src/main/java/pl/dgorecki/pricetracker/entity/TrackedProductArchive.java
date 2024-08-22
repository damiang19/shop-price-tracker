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
@Table(name = "tracked_product_archive")
public class TrackedProductArchive {

    @Id
    @Column(name = "tracked_product_archive_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tracked_product_id")
    private TrackedProduct trackedProduct;

    private Instant created;

    @Column(name = "price")
    private BigDecimal price;

}
