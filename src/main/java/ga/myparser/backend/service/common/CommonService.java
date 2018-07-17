package ga.myparser.backend.service.common;

import java.util.List;

public interface CommonService {
    List<ProductPoolPartyToUpdate> getProductsToUpdatePoolParty();
    void updateSneakersPoolParty();
    List<ProductFreeRunToUpdate> getProductsToUpdateFreeRun();
    void updateSneakersFreeRun();
}
