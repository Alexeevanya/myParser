package ga.myparser.backend.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "oc_product_option_value")
public class OcProductOptionValueEntity {
    private int productOptionValueId;
    private int productOptionId;
    private int productId;
    private int optionId;
    private int optionValueId;
    private int quantity;
    private byte subtract;
    private BigDecimal price;
    private String pricePrefix;
    private int points;
    private String pointsPrefix;
    private BigDecimal weight;
    private String weightPrefix;

    @Id
    @Column(name = "product_option_value_id")
    public int getProductOptionValueId() {
        return productOptionValueId;
    }

    public void setProductOptionValueId(int productOptionValueId) {
        this.productOptionValueId = productOptionValueId;
    }

    @Basic
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
    @Column(name = "option_value_id")
    public int getOptionValueId() {
        return optionValueId;
    }

    public void setOptionValueId(int optionValueId) {
        this.optionValueId = optionValueId;
    }

    @Basic
    @Column(name = "quantity")
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Basic
    @Column(name = "subtract")
    public byte getSubtract() {
        return subtract;
    }

    public void setSubtract(byte subtract) {
        this.subtract = subtract;
    }

    @Basic
    @Column(name = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Basic
    @Column(name = "price_prefix")
    public String getPricePrefix() {
        return pricePrefix;
    }

    public void setPricePrefix(String pricePrefix) {
        this.pricePrefix = pricePrefix;
    }

    @Basic
    @Column(name = "points")
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Basic
    @Column(name = "points_prefix")
    public String getPointsPrefix() {
        return pointsPrefix;
    }

    public void setPointsPrefix(String pointsPrefix) {
        this.pointsPrefix = pointsPrefix;
    }

    @Basic
    @Column(name = "weight")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Basic
    @Column(name = "weight_prefix")
    public String getWeightPrefix() {
        return weightPrefix;
    }

    public void setWeightPrefix(String weightPrefix) {
        this.weightPrefix = weightPrefix;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OcProductOptionValueEntity that = (OcProductOptionValueEntity) o;
        return productOptionValueId == that.productOptionValueId &&
                productOptionId == that.productOptionId &&
                productId == that.productId &&
                optionId == that.optionId &&
                optionValueId == that.optionValueId &&
                quantity == that.quantity &&
                subtract == that.subtract &&
                points == that.points &&
                Objects.equals(price, that.price) &&
                Objects.equals(pricePrefix, that.pricePrefix) &&
                Objects.equals(pointsPrefix, that.pointsPrefix) &&
                Objects.equals(weight, that.weight) &&
                Objects.equals(weightPrefix, that.weightPrefix);
    }

    @Override
    public int hashCode() {

        return Objects.hash(productOptionValueId, productOptionId, productId, optionId, optionValueId, quantity, subtract, price, pricePrefix, points, pointsPrefix, weight, weightPrefix);
    }
}
