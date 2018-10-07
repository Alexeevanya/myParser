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
    public List<Integer> getIdBySku(String sku) {
        Query query = em.createQuery("select p.id from ProductFreeRun p where p.sku = :sku");
        query.setParameter("sku", sku);
        try {
            return (List<Integer>) query.getResultList();
        } catch (NoResultException e){
            return Collections.EMPTY_LIST;
        }
    }

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
    @Transactional
    public void deleteOldOptions(int id, int optionNameId) {
        em.createQuery("delete from ProductOptionValueFR p where p.productId= :id and p.optionId= :optionNameId").
                setParameter("id", id).
                setParameter("optionNameId", optionNameId).
                executeUpdate();
    }

    @Override
    @Transactional
    public void updatePriceAndQuantity(int productId, BigDecimal price, int quantity) {
        em.createQuery("update ProductFreeRun p set p.price= ?1, p.quantity= ?2 where p.id= ?3").
                setParameter(1, price).
                setParameter(2, quantity).
                setParameter(3, productId).
                executeUpdate();
    }

    @Override
    @Transactional
    public void updateOption(int productId, int productOptionId, int optionId, int optionValueId) {
        Query query = em.createNativeQuery("INSERT INTO oc_product_option_value VALUES" +
                "(DEFAULT,?,?,?,?,1000,1,0.0000,'+',0,'+',0.00000000,'+')")
                .setParameter(1, productOptionId)
                .setParameter(2, productId)
                .setParameter(3, optionId)
                .setParameter(4, optionValueId);
        query.executeUpdate();
    }

    @Override
    public int getProductOptionId(int productId, int optionId) {
        try {
            return (int) em.createQuery("select p.productOptionId from ProductOptionFR p where p.productId = :productId and p.optionId = :optionId").
                    setParameter("productId", productId).
                    setParameter("optionId", optionId).
                    getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean findProductByOptionNameId(int commonOptionName, int productId) {
            return (boolean) em.createQuery("select case when count(p.productOptionId) = 1 then true else false end from ProductOptionFR p where p.optionId= :commonOptionName and p.productId= :productId").
                    setParameter("commonOptionName", commonOptionName).
                    setParameter("productId", productId).
                    getSingleResult();
    }
}
