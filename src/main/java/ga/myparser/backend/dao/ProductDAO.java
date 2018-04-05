package ga.myparser.backend.dao;

import java.util.List;

public interface ProductDAO {

    List<Integer> getMyIdByFrId(int frId);

    void deleteOldOptions(int productId, int optionId);

    int getProductOptionId(Integer productId);

    void updateOptions(int productId, int productOptionId, int optionId, int optionValueId);
}
