package ga.myparser.backend.service.common.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class CommonServiceImplTest {

    @Test
    public void getProductsToUpdateFreeRun() {

        CommonServiceImpl commonService = new CommonServiceImpl();

        commonService.getProductsToUpdateFreeRun();

    }

//    @Test
//    public void convertOptionsToList() {
//
//        CommonServiceImpl commonService = new CommonServiceImpl();
//
//        Set<Integer> result = commonService.convertOptionsToList("36.5,37");
//        int expected = 37;
//        int actual = 0;
//        for (Integer integer : result) {
//            actual = integer;
//        }
//        assertEquals(expected, actual);
//
//    }
}