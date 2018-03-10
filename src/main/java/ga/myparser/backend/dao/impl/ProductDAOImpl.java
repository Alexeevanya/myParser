package ga.myparser.backend.dao.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Product product) {
        em.persist(product);
    }
}
