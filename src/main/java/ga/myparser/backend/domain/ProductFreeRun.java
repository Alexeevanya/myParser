package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "oc_product")
public class ProductFreeRun {

    @Id
    @NonNull
    @Column(name = "product_id")
    private int id;

    @NonNull
    @Column(name = "sku")
    private String sku;

    @NonNull
    @Column(name = "upc")
    private String upc;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

}
