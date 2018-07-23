package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "oc_product_option_value")
public class ProductOptionValueFR {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_option_value_id")
    private int productOptionValueId;

    @NonNull
    @Column(name = "product_option_id")
    private int productOptionId;

    @NonNull
    @Column(name = "product_id")
    private int productId;

    @NonNull
    @Column(name = "option_id")
    private int optionId;

    @NonNull
    @Column(name = "option_value_id")
    private int optionValueId;

    @NonNull
    @Column(name = "quantity")
    private int quantity;

    @NonNull
    @Column(name = "subtract")
    private int subtract = 1;

    @NonNull
    @Column(name = "price")
    private BigDecimal price = new BigDecimal(0);

    @NonNull
    @Column(name = "price_prefix")
    private String pricePrefix = "+";

    @NonNull
    @Column(name = "points")
    private int points = 0;

    @NonNull
    @Column(name = "points_prefix")
    private String pointsPrefix = "+";

    @NonNull
    @Column(name = "weight")
    private BigDecimal weight = new BigDecimal(0);

    @NonNull
    @Column(name = "weight_prefix")
    private String weightPrefix = "+";

}
