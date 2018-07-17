package ga.myparser.backend.service.freeRun.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.service.common.CommonService;
import ga.myparser.backend.service.common.ProductFreeRunToUpdate;
import ga.myparser.backend.service.freeRun.FreeRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private void updateProducts(List<ProductFreeRunToUpdate> listFreeRunProductsToUpdate) {
        for (ProductFreeRunToUpdate product : listFreeRunProductsToUpdate) {
            productDAO.updateProductsFreeRun(product.getModel(), product.getQuantity(), product.getPrice());
        }
    }
}
