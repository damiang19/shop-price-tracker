package pl.dgorecki.scrapper.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.dgorecki.scrapper.enums.JsonRegex;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productNameHtmlClass;

    private String priceHtmlClass;

    private String name;

    private String shopUrl;

    @Enumerated(EnumType.STRING)
    private JsonRegex jsonRegex;

}
