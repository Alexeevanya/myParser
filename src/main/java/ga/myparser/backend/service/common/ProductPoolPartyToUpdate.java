package ga.myparser.backend.service.common;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductPoolPartyToUpdate {
        private String model;
        private int quantity;
        private BigDecimal price;
    }