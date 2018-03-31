package ga.myparser.backend.dao.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(Product product) {
        em.persist(product);
    }

    @Override
    public List<Integer> getMyIdByFRId(int frId) {
        Query query = em.createQuery("select m.myProductId from MyProductIdToFreeRunId m where m.freRunProductId = :frId");
        query.setParameter("frId", frId);

        try {
            return (List<Integer>) query.getResultList();
        } catch (NoResultException e){
            return Collections.emptyList();
        }
    }
}
