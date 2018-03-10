package ga.myparser.backend.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "oc_product_to_category")
@IdClass(OcProductToCategoryEntityPK.class)
public class OcProductToCategoryEntity {
    private int productId;
    private int categoryId;
    private byte mainCategory;

    @Id
    @Column(name = "product_id")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Id
    @Column(name = "category_id")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Basic
    @Column(name = "main_category")
    public byte getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(byte mainCategory) {
        this.mainCategory = mainCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OcProductToCategoryEntity that = (OcProductToCategoryEntity) o;
        return productId == that.productId &&
                categoryId == that.categoryId &&
                mainCategory == that.mainCategory;
    }

    @Override
    public int hashCode() {

        return Objects.hash(productId, categoryId, mainCategory);
    }
}
