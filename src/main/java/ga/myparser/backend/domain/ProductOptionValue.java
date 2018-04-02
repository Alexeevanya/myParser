package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import java.text.DecimalFormat;

@Getter
@Setter
@Entity
@Table(name = "oc_product_option_value")
public class ProductOptionValue {

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
    private int subtract;

    @NonNull
    @Column(name = "price")
    private DecimalFormat price;

    @NonNull
    @Column(name = "price_prefix")
    private String pricePrefix;

    @NonNull
    @Column(name = "points")
    private int points;

    @NonNull
    @Column(name = "points_prefix")
    private String pointsPrefix;

    @NonNull
    @Column(name = "weight")
    private DecimalFormat weight;

    @NonNull
    @Column(name = "weight_prefix")
    private DecimalFormat weightPrefix;
}
