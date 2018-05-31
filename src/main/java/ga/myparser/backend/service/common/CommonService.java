package ga.myparser.backend.service.common;

import java.util.List;

public interface CommonService {
    void updateSneakersPoolParty();
    List<ProductPoolPartyToUpdate> getProductsToUpdatePoolParty();
}
