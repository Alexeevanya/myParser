package ga.myparser.backend.service;

import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

public interface ProductService {

    ArrayList<String> getListCategoryToParse(Document category);

    ArrayList<String> getListProductsToParse(List<String> listCategoryToParse);

    ArrayList<String> saveProducts(ArrayList<String> listProductsToParse);

}
