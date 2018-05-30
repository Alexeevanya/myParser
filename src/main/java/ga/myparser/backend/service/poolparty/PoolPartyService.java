package ga.myparser.backend.service.poolparty;

import ga.myparser.backend.service.poolparty.impl.PoolPartyServiceImpl.ProductPoolPartyToUpdate;

import java.util.List;

public interface PoolPartyService {

    List<ProductPoolPartyToUpdate> getProductsToUpdateFromXML();

    void start();
}
