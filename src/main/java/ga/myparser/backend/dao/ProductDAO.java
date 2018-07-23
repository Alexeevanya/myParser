package ga.myparser.backend.dao;

import ga.myparser.backend.domain.ProductFreeRun;

import java.math.BigDecimal;
import java.util.List;

public interface ProductDAO {

    ProductFreeRun findByModel(String model);

    List<String> getAllModelProductsPoolParty(int idManufacturer);

    void updateProductsPoolParty(String model, int quantity, BigDecimal price);

    void updateQuantity(String productModel);

    int getIdManufacturerByName(String name);

    void updateList(List<ProductFreeRun> list);

    int getOptionIdByName(String optionName);
}
