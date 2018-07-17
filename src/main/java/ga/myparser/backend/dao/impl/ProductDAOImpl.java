package ga.myparser.backend.dao.impl;

import ga.myparser.backend.dao.ProductDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Repository
public class ProductDAOImpl implements ProductDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int getIdManufacturerByName(String name) {
        Query query = em.createNativeQuery("SELECT manufacturer_id FROM oc_manufacturer WHERE name = :name");
        query.setParameter("name", name);
        return (int) query.getSingleResult();
    }

    @Override
    public List<String> getAllModelProductsPoolParty(int idManufacturer) {
        Query query = em.createQuery("select p.sku from ProductPoolParty p where p.manufacturer_id= :idManufacturer");
        query.setParameter("idManufacturer", idManufacturer);
        try {
            return (List<String>) query.getResultList();
        } catch (NoResultException e){
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public void updateProductsPoolParty(String model, int quantity, BigDecimal price) {
        Query query = em.createQuery("update ProductPoolParty p set p.quantity = :quantity, p.price = :price where p.sku = :model");
        query.setParameter("quantity", quantity)
                .setParameter("price", price)
                .setParameter("model", model);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void updateQuantity(String productModel) {
        Query query = em.createQuery("update ProductPoolParty p set p.quantity = 0 where p.sku = :productModel");
        query.setParameter("productModel", productModel);
        query.executeUpdate();
    }

    @Override
    public List<Integer> getProductsByUpc() {
        Query query = em.createQuery("select p.sku from ProductFreeRun p where p.upc= :upc");
        query.setParameter("upc", 1);
        try {
            return (List<Integer>) query.getResultList();
        } catch (NoResultException e){
            return Collections.emptyList();
        }
    }
}
