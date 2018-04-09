package ga.myparser.backend.service;

import org.jsoup.nodes.Document;

import java.util.List;

public interface ProductService {

    List<String> getListCategoryToParse(Document category);

    List<String> getListProductsToParse(List<String> listCategoryToParse);

    List<String> parseProducts(List<String> listProductsToParse);

    List<String> startParseCategory(String categoryURL);
}
