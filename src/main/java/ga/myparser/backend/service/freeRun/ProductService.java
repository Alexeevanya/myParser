package ga.myparser.backend.service.freeRun;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ProductService {

    void startParseCatalog(String catalogURL);

    List<String> getListCatalogToParse(Document listCatalog);

    List<String> getListCategoryToParse(List<String> listCatalogToParse);

    List<String> getListProductsToParse(List<String> listCategoryToParse);

    void parseProducts(List<String> listProductsToParse);

}
