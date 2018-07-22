package ga.myparser.backend.domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "oc_product_option")
public class ProductOptionFR {

    @Id
    @NonNull
    @Column(name = "product_option_id")
    private int productOptionId;

    @NonNull
    @Column(name = "product_id")
    private int productId;

    @NonNull
    @Column(name = "option_id")
    private int optionId;
}
