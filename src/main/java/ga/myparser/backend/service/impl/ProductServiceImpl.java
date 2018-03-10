package ga.myparser.backend.service.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.domain.Product;
import ga.myparser.backend.service.ProductService;
import ga.myparser.backend.util.ProductUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Value("${custom.ftp.dome}")
    private String ftpDomain;

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

                Document doc = Jsoup.connect(productURL).get();// проверить есть ли  уменя такой товар и обновить опции

                String productName = ProductUtil.getName(doc);
                ArrayList<String> listURLsImages = ProductUtil.getListURLsImages(doc);
                ProductUtil.uploadImages(listURLsImages, productName);
//                LinkedHashSet<Integer> listOptions = ProductUtil.getOptions(doc);
                int price = ProductUtil.getPrice(doc);
                Map<String, String> mapTable = ProductUtil.getMapTable(doc);

//                Product product = new Product(productName);
//                productDAO.save(product);
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return listProductsToParse;
    }
}
