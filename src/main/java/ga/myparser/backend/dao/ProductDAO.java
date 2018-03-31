package ga.myparser.backend.dao;

import ga.myparser.backend.domain.Product;

import java.util.List;

public interface ProductDAO {

    void save(Product product);

    List<Integer> getMyIdByFRId(int frId);
}
