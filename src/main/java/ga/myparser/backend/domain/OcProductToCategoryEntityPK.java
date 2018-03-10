package ga.myparser.backend.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class OcProductToCategoryEntityPK implements Serializable {
    private int productId;
    private int categoryId;

    @Column(name = "product_id")
    @Id
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Column(name = "category_id")
    @Id
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OcProductToCategoryEntityPK that = (OcProductToCategoryEntityPK) o;
        return productId == that.productId &&
                categoryId == that.categoryId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, categoryId);
    }
}
