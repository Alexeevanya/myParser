package ga.myparser.backend.service.common;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductFreeRunToUpdate {
    private int id;
    private String sku;
    private int quantity;
    private BigDecimal price;
    private int optionId;
    private Set<Integer> options;
    private int category;

    public ProductFreeRunToUpdate(int id, String sku, int quantity, BigDecimal price, int optionId, Set<Integer> options) {
        this.id = id;
        this.sku = sku;
        this.quantity = quantity;
        this.price = price;
        this.optionId = optionId;
        this.options = options;
    }

    public ProductFreeRunToUpdate(){}
}
