package ga.myparser.backend.service.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.domain.Product;
import ga.myparser.backend.service.ParsProduct;
import ga.myparser.backend.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;

    private final ParsProduct parsProduct;

    @Autowired
    public ProductServiceImpl(ProductDAO productDAO, ParsProduct parsProduct) {
        this.productDAO = productDAO;
        this.parsProduct = parsProduct;
    }

    @Override
    public ArrayList<String> getListCategoryToParse(Document category) {
        Elements elements = category.select("div.oPager");
        ArrayList<String> listCategoryToParse = new ArrayList<>();
        listCategoryToParse.add(category.location());
        for (Element element : elements.select("a")) {
            listCategoryToParse.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        return listCategoryToParse;
    }

    @Override
    public ArrayList<String> getListProductsToParse(List<String> listCategoryToParse) {
        Document doc;
        ArrayList<String> listProductsToParse = new ArrayList<>();

        for(String cat : listCategoryToParse){
            try {
                doc = Jsoup.connect(cat).get();
                Elements elements = doc.select("div.items");
                for (Element element : elements.select("a.button")) {
                    listProductsToParse.add("http://free-run.kiev.ua/" + element.attr("href"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listProductsToParse;
    }

    @Override
    public ArrayList<String> saveProducts(ArrayList<String> listProductsToParse) {
        try {
            for (String productURL : listProductsToParse) {

                Document doc = Jsoup.connect(productURL).get();

                String productName = parsProduct.getName(doc);
                ArrayList<String> listURLsImages = parsProduct.getListURLsImages(doc);
                parsProduct.uploadImages(listURLsImages, productName);
                LinkedHashSet<Integer> listOptions = parsProduct.getOptions(doc);
                int price = parsProduct.getPrice(doc);
                Map<String, String> mapTable = parsProduct.getMapTable(doc);

//                Product product = new Product(listOptions);
//                productDAO.save(product);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return listProductsToParse;
    }
}
