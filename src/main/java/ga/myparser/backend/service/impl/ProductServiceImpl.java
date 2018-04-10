package ga.myparser.backend.service.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.exception.URLNotValidException;
import ga.myparser.backend.service.ParsProduct;
import ga.myparser.backend.service.ProductService;
import ga.myparser.backend.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final ParsProduct parsProduct;

    @Override
    public List<String> startParseCatalog(String catalogURL) {
        if(!Utils.urlIsValid(catalogURL)){
            throw new URLNotValidException(catalogURL);
        }

        Document catalogURLs = null;
        try {
            catalogURLs = Jsoup.connect(catalogURL).get();
        } catch (IOException e) {
            log.warn("Can't parse URL {}", catalogURL);
        }

        List<String> listCatalogToParse = getListCatalogToParse(catalogURLs);

        List<String> listCategoryToParse = getListCategoryToParse(listCatalogToParse);

        List<String> listProductsToParse = getListProductsToParse(listCategoryToParse);

        List<String> successList = parseProducts(listProductsToParse);

        if(successList.isEmpty()){
            successList = new ArrayList<>();
            successList.add("There are no matching items to update options");
        }
        return successList;
    }

    @Override
    public List<String> getListCatalogToParse(Document listCatalog) {
        List<String> catalogUrls = new ArrayList<>();
        Elements elements = listCatalog.select("div.item");
        for (Element element : elements.select("div.img").select("a")) {
            catalogUrls.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        return catalogUrls;
    }

    @Override
    public List<String> getListCategoryToParse(List<String> listCatalogToParse) {
        Document doc;
        List<String> listCategoryToParse = new ArrayList<>();
        for (String catalogUrl : listCatalogToParse) {
            try {
                doc = Jsoup.connect(catalogUrl).get();
                Elements elements = doc.select("div.oPager");
                listCategoryToParse.add(doc.location());
                for (Element element : elements.select("a")) {
                    listCategoryToParse.add("http://free-run.kiev.ua/" + element.attr("href"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(listCatalogToParse.isEmpty()){
            log.info("Subcategories not found on page");
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
    public List<String> parseProducts(List<String> listProductsToParse) {
        List<String> listUpdatedOptions = new ArrayList<>();
        List<Integer> listNewProductsToParse = new ArrayList<>(); //todo add parse new products
        for (String productURL : listProductsToParse) {

            int frProductId = parsProduct.getProductIdFromUrl(productURL);
            List<Integer> listMyProductId = productDAO.getMyIdByFrId(frProductId);
            if (listMyProductId.isEmpty()) {
                listNewProductsToParse.add(frProductId);
            } else {
                String updatedUrl = updateProductOptions(productURL, listMyProductId);
                if(updatedUrl != null){
                    listUpdatedOptions.add(updatedUrl);
                }
            }
        }
        return listUpdatedOptions;
    }

    private String updateProductOptions(String productURL, List<Integer> listMyProductIdToUpdate){
        Document doc = null;
        try {
            doc = Jsoup.connect(productURL).get();
        } catch (IOException e) {
            log.warn("Can't connect to product url - {}", productURL);
        }
        Set<Integer> listOptions = parsProduct.getOptions(doc);
        if (listOptions.isEmpty()) {
            for (Integer productId : listMyProductIdToUpdate) {
                productDAO.nullifyAvailability(productId);
            }
        } else {
            Set<Integer> listOptionsValuesIds = convertOptionsValuesToIds(listOptions);
            if (listOptionsValuesIds != null) {
                for (Integer productId : listMyProductIdToUpdate) {
                    int optionId = 13;
                    productDAO.deleteOldOptions(productId, optionId);
                    int productOptionId = productDAO.getProductOptionId(productId);

                    for (Integer optionValueId : listOptionsValuesIds) {
                        productDAO.updateOptions(productId, productOptionId, optionId, optionValueId);
                        log.info("Updated in product id {}", productId);
                    }
                }
                return productURL;
            }
        }
        return null;
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
