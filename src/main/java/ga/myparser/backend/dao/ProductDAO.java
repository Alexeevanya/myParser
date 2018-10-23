package ga.myparser.backend.dao;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDAO {

    List <Integer> getIdBySku(String sku);

    List<String> getAllModelProductsPoolParty(int idManufacturer);

    void updateProductsPoolParty(String model, int quantity, BigDecimal price);

    void updateQuantity(String productModel);

    int getIdManufacturerByName(String name);

    void deleteOldOptions(int id, int optionNameId);

    void updatePriceAndQuantity(int productId, BigDecimal price, int quantity);

    void updateOption(int productId, int productOptionId, int optionId, int i);

    int getProductOptionId(int productId, int optionId);

    boolean findProductByOptionNameId(int commonOptionName, int productId);
}
