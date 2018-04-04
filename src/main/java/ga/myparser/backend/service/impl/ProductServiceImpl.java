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
                Set<Integer> listOptionsValuesIds = convertOptionsValuesToIds(listOptions);

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

    private Set<Integer> convertOptionsValuesToIds(Set<Integer> listOptions) {
        Set<Integer> list = new HashSet<>();
        for (Integer listOption : listOptions) {
            switch (listOption){
                case 35: list.add(49); break;
                case 36: list.add(50); break;
                case 37: list.add(51); break;
                case 38: list.add(52); break;
                case 39: list.add(53); break;
                case 40: list.add(54); break;
                case 41: list.add(55); break;
                case 42: list.add(56); break;
                case 43: list.add(57); break;
                case 44: list.add(58); break;
                case 45: list.add(59); break;
                case 46: list.add(60); break;
            }
        }
        return list;
    }
}
