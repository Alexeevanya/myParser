package ga.myparser.backend.dao.impl;

import ga.myparser.backend.dao.ProductDAO;
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
    public int getProductOptionId(Integer productId) {
        Query query = em.createQuery("select p.productOptionId from ProductOption p where p.productId= :productId");
        query.setParameter("productId", productId);
        return (int) query.getSingleResult();
    }

    @Override
    @Transactional
    public void updateOptions(int productId, int productOptionId, int optionId, int optionValueId) {
        Query query = em.createNativeQuery("INSERT INTO oc_product_option_value VALUES" +
                "(DEFAULT,?,?,?,?,1000,1,0.0000,'+',0,'+',0.00000000,'+')")
                .setParameter(1, productOptionId)
                .setParameter(2, productId)
                .setParameter(3, optionId)
                .setParameter(4, optionValueId);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void nullifyAvailability(Integer productId) {
        Query query = em.createNativeQuery("UPDATE oc_product SET quantity = ? WHERE product_id = ?")
                .setParameter(1, 0)
                .setParameter(2, productId);
        query.executeUpdate();
    }
}
