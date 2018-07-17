package ga.myparser.backend.service.freeRun.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.service.freeRun.FreeRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeRunServiceImpl implements FreeRunService {

    @Autowired
    ProductDAO productDAO;

    @Override
    public void start() {

        List<Integer> listProductsToUpdate = productDAO.getProductsByUpc();
    }
}
