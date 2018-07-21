package ga.myparser.backend.service.common;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class ProductFreeRunToUpdate {
    private String model;
    private int quantity;
    private BigDecimal price;
    private Set<Integer> options;
    private int category;
}
