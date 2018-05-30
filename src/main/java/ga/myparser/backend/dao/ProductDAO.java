package ga.myparser.backend.dao;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDAO {

    List<String> getAllModelProductsPoolParty(int idManufacturer);

    void updateProductsPoolParty(String model, int quantity, BigDecimal price);

    void updateQuantity(String productModel);

    int getIdManufacturerByName(String name);
}
