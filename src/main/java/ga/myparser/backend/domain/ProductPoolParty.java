package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "oc_product")
public class ProductPoolParty {

    @Id
    @NonNull
    @Column(name = "product_id")
    private int id;

    @NonNull
    @Column(name = "sku")
    private String sku;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "manufacturer_id")
    private int manufacturer_id;
}
