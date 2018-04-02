package ga.myparser.backend.dao.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.domain.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
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
    public List<Integer> getMyIdByFrId(int frId) {
        Query query = em.createQuery("select m.myProductId from MyProductIdToFreeRunId m where m.freRunProductId = :frId");
        query.setParameter("frId", frId);

        try {
            return (List<Integer>) query.getResultList();
        } catch (NoResultException e){
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void deleteOldOptions(int productId, int optionId) {
        Query query = em.createQuery("delete from ProductOptionValue where productId= :productId and optionId= :optionId");
        query.setParameter("productId", productId)
                .setParameter("optionId", optionId)
                .executeUpdate();
    }

    @Override
    public void updateOptions(int productId, int optionId, int valueOption) {
        Query query = em.createNativeQuery("INSERT INTO oc_product_option_value VALUES" +
                "(,227,65,13,55,1000,1,0.0000,+,0,+,0.00000000,+)");
    }
}
