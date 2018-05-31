package ga.myparser.backend.service.poolparty.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.service.common.CommonService;
import ga.myparser.backend.service.common.ProductPoolPartyToUpdate;
import ga.myparser.backend.service.poolparty.PoolPartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PoolPartyServiceImpl implements PoolPartyService {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    CommonService commonService;

    @Scheduled(initialDelay = 5000, fixedDelay = 4320 * 10000)
    private void scheduleStart() {
        log.info("Update PoolParty Service started");
        start();
        log.info("Update PoolParty Service finished");
    }


    public void start(){
        int idManufacturerPoolParty = productDAO.getIdManufacturerByName("PoolParty");

        List<String> allProductsPoolParty = productDAO.getAllModelProductsPoolParty(idManufacturerPoolParty);

        List<ProductPoolPartyToUpdate> listToUpdate = commonService.getProductsToUpdatePoolParty();

        updateProducts(listToUpdate);

        List<String> listToZeroQuantity = getListToZeroQuantity(allProductsPoolParty, listToUpdate);

        updateQuantity(listToZeroQuantity);

    }

    private void updateProducts(List<ProductPoolPartyToUpdate> listToUpdate) {
        for (ProductPoolPartyToUpdate product : listToUpdate) {
            productDAO.updateProductsPoolParty(product.getModel(), product.getQuantity(), product.getPrice());
        }
    }

    private List<String> getListToZeroQuantity(List<String> allProductsPoolParty, List<ProductPoolPartyToUpdate> listToUpdate) {
        List<String> zeroQuantity = new ArrayList<>();
        for (String entry : allProductsPoolParty) {
            boolean found = false;
            for (ProductPoolPartyToUpdate update : listToUpdate) {
                if (update.getModel().equals(entry)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                zeroQuantity.add(entry);
            }
        }
        return zeroQuantity;
    }

    private void updateQuantity(List<String> listToZeroQuantity) {
        for (String productModel : listToZeroQuantity) {
            productDAO.updateQuantity(productModel);
        }
    }
}
