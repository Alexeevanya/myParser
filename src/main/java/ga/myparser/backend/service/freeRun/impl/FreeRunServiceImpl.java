package ga.myparser.backend.service.freeRun.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.service.common.CommonService;
import ga.myparser.backend.service.common.ProductFreeRunToUpdate;
import ga.myparser.backend.service.freeRun.FreeRunService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class FreeRunServiceImpl implements FreeRunService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CommonService commonService;

    @Scheduled(initialDelay = 100000, fixedDelay = 4320 * 10000)
    private void scheduleStart() {
        log.info("Update FreeRun Service started");
        start();
        log.info("Update FreeRun Service finished");
    }

    @Override
    public void start() {

        List<ProductFreeRunToUpdate> listFreeRunProducts = commonService.getProductsToUpdateFreeRun();

        List<ProductFreeRunToUpdate> productsFreeRunToUpdate = getProductsToUpdate(listFreeRunProducts);

        updateProducts(productsFreeRunToUpdate);

    }

    private void updateProducts(List<ProductFreeRunToUpdate> productFreeRunToUpdate) {
        for (ProductFreeRunToUpdate product : productFreeRunToUpdate) {
            productDAO.updatePriceAndQuantity(product.getId(), product.getPrice(), product.getQuantity());
            if(!product.getOptions().isEmpty()){
                int productId = product.getId();
                int optionId = product.getOptionId();
                int productOptionId = productDAO.getProductOptionId(productId, optionId);
                Set<Integer> options = product.getOptions();
                for (Integer option : options) {
                    productDAO.updateOption(productId, productOptionId, optionId, option);
                }
            }
        }
    }

    private List<ProductFreeRunToUpdate> getProductsToUpdate(List<ProductFreeRunToUpdate> updates) {
        List<ProductFreeRunToUpdate> myProductsToUpdate = new ArrayList<>();

        for (ProductFreeRunToUpdate update : updates) {
            String sku = update.getSku();
            int id = productDAO.getIdBySku(sku);
            if (id == 0) {
                continue;
            }
            int quantity = update.getQuantity();
            BigDecimal price = update.getPrice();

            int commonOptionName = 13;
            int childOptionName = 17;
            int optionId = 0;
            Set<Integer> options = Collections.emptySet();

            boolean findManAndWomanOption = productDAO.findProductByOptionNameId(commonOptionName, id);
            if(findManAndWomanOption){
                options = convertCommonOptions(update.getOptions());
                productDAO.deleteOldOptions(id, commonOptionName);
                optionId = commonOptionName;
            }

            boolean findChildOption = productDAO.findProductByOptionNameId(childOptionName, id);
            if(findChildOption){
                options = convertChildOptions(update.getOptions());
                productDAO.deleteOldOptions(id, childOptionName);
                optionId = childOptionName;
            }

            if(!findChildOption && !findManAndWomanOption){
                ProductFreeRunToUpdate myProduct = new ProductFreeRunToUpdate(
                        id, sku, quantity, price, optionId, options
                );
                myProductsToUpdate.add(myProduct);
                continue;
            }

            ProductFreeRunToUpdate myProduct = new ProductFreeRunToUpdate(
                    id, sku, quantity, price, optionId, options
            );
            myProductsToUpdate.add(myProduct);
        }
        return myProductsToUpdate;
    }

    private Set<Integer> convertChildOptions(Set<Integer> options) {
        if(options == null){
            return Collections.emptySet();
        }
        Set<Integer> listOptionsId = new HashSet<>();
        for (Integer option : options) {
            switch (option){
                case 20: listOptionsId.add(74); break;
                case 21: listOptionsId.add(75); break;
                case 22: listOptionsId.add(76); break;
                case 23: listOptionsId.add(77); break;
                case 24: listOptionsId.add(78); break;
                case 25: listOptionsId.add(79); break;
                case 26: listOptionsId.add(80); break;
                case 27: listOptionsId.add(81); break;
                case 28: listOptionsId.add(82); break;
                case 29: listOptionsId.add(83); break;
                case 30: listOptionsId.add(84); break;
                case 31: listOptionsId.add(85); break;
                case 32: listOptionsId.add(86); break;
                case 33: listOptionsId.add(87); break;
                case 34: listOptionsId.add(88); break;
                case 35: listOptionsId.add(89); break;
                case 36: listOptionsId.add(90); break;
            }
        }
        return listOptionsId;
    }

    private Set<Integer> convertCommonOptions(Set<Integer> options) {
        if(options == null){
            return Collections.emptySet();
        }
        Set<Integer> listOptionsId = new HashSet<>();
        for (Integer option : options) {
            switch (option){
                case 35: listOptionsId.add(49); break;
                case 36: listOptionsId.add(50); break;
                case 37: listOptionsId.add(51); break;
                case 38: listOptionsId.add(52); break;
                case 39: listOptionsId.add(53); break;
                case 40: listOptionsId.add(54); break;
                case 41: listOptionsId.add(55); break;
                case 42: listOptionsId.add(56); break;
                case 43: listOptionsId.add(57); break;
                case 44: listOptionsId.add(58); break;
                case 45: listOptionsId.add(59); break;
                case 46: listOptionsId.add(60); break;
            }
        }
        return listOptionsId;
    }
}
