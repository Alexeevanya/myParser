package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "oc_product")
public class Product {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "model")
    private String model;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "stock_status_id")
    private int stockStatusId;

    @Column(name = "price")
    private BigDecimal price;

}
