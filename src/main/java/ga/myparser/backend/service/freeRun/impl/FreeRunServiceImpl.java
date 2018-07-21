package ga.myparser.backend.service.freeRun.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.domain.ProductFreeRun;
import ga.myparser.backend.service.common.CommonService;
import ga.myparser.backend.service.common.ProductFreeRunToUpdate;
import ga.myparser.backend.service.freeRun.FreeRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FreeRunServiceImpl implements FreeRunService {

    @Autowired
    ProductDAO productDAO;

    @Autowired
    CommonService commonService;

    @Override
    public void start() {

        List<ProductFreeRunToUpdate> listFreeRunProductsToUpdate = commonService.getProductsToUpdateFreeRun();

        updateProducts(listFreeRunProductsToUpdate);
    }

    private void updateProducts(List<ProductFreeRunToUpdate> updates) {
        List<ProductFreeRun> listToUpdate = new ArrayList<>();
        for (ProductFreeRunToUpdate update : updates) {
            ProductFreeRun product = productDAO.findByModel(update.getModel());
            if (product != null) {
                product.setPrice(update.getPrice());
                product.setQuantity(update.getQuantity());
                listToUpdate.add(product);
            }
        }
        productDAO.updateList(listToUpdate);
    }
}
