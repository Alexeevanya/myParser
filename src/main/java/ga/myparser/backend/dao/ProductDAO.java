package ga.myparser.backend.dao;

import ga.myparser.backend.domain.Product;

import java.util.List;

public interface ProductDAO {

    void save(Product product);

    List<Integer> getMyIdByFrId(int frId);

    void updateOptions(int productId, int optionId, int valueOption);

    void deleteOldOptions(int productId, int optionId);
}
