package ga.myparser.backend.service.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.service.ParsProduct;
import ga.myparser.backend.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
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
    public List<String> getListCategoryToParse(Document category) {
        Elements elements = category.select("div.oPager");
        List<String> listCategoryToParse = new ArrayList<>();
        listCategoryToParse.add(category.location());
        for (Element element : elements.select("a")) {
            listCategoryToParse.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        return listCategoryToParse;
    }

    @Override
    public List<String> getListProductsToParse(List<String> listCategoryToParse) {
        Document doc;
        List<String> listProductsToParse = new ArrayList<>();

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
    public void parseProducts(List<String> listProductsToParse) {
        for (String productURL : listProductsToParse) {

            int frProductId = parsProduct.getProductIdFromUrl(productURL);
            List<Integer> listMyProductId = productDAO.getMyIdByFrId(frProductId);

            if(!listMyProductId.isEmpty()){
                Document doc = null;
                try {
                    doc = Jsoup.connect(productURL).get();
                } catch (IOException e) {
                    log.warn("Can't connect to product url - {}", productURL);
                }
                Set<Integer> listOptions = parsProduct.getOptions(doc);
                Set<Integer> listOptionsValuesIds = getOptionsValuesIds(listOptions);

                if(listOptionsValuesIds != null){
                    for (Integer productId : listMyProductId) {
                        int optionId = 13;
                        productDAO.deleteOldOptions(productId, optionId);
                        int productOptionId = productDAO.getProductOptionId(productId);
                        for (Integer optionValueId : listOptionsValuesIds) {
                            productDAO.updateOptions(productId, productOptionId, optionId, optionValueId);
                            log.info("here {}", productId);
                        }
                    }
                }
            }
        }
    }

    private Set<Integer> getOptionsValuesIds(Set<Integer> listOptions) {
        //todo change values to id
        return listOptions;
    }
}
