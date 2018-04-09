package ga.myparser.backend.service;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ProductService {

    List<String> startParseCatalog(String catalogURL);

    List<String> getListCatalogToParse(Document listCatalog);

    List<String> getListCategoryToParse(List<String> listCatalogToParse);

    List<String> getListProductsToParse(List<String> listCategoryToParse);

    List<String> parseProducts(List<String> listProductsToParse);

}
