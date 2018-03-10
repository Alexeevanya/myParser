package ga.myparser.backend.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "oc_product_option")
public class OcProductOptionEntity {
    private int productOptionId;
    private int productId;
    private int optionId;
    private String optionValue;
    private byte required;

    @Id
    @Column(name = "product_option_id")
    public int getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(int productOptionId) {
        this.productOptionId = productOptionId;
    }

    @Basic
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Basic
    @Column(name = "option_id")
    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    @Basic
    @Column(name = "option_value")
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Basic
    @Column(name = "required")
    public byte getRequired() {
        return required;
    }

    public void setRequired(byte required) {
        this.required = required;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OcProductOptionEntity that = (OcProductOptionEntity) o;
        return productOptionId == that.productOptionId &&
                productId == that.productId &&
                optionId == that.optionId &&
                required == that.required &&
                Objects.equals(optionValue, that.optionValue);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productOptionId, productId, optionId, optionValue, required);
    }
}
