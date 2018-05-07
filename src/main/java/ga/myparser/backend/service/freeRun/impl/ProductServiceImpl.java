package ga.myparser.backend.service.freeRun.impl;

import ga.myparser.backend.dao.ProductDAO;
import ga.myparser.backend.exception.URLNotValidException;
import ga.myparser.backend.service.freeRun.ParsProduct;
import ga.myparser.backend.service.freeRun.ProductService;
import ga.myparser.backend.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDAO productDAO;
    private final ParsProduct parsProduct;

    @Scheduled(initialDelay = 5000, fixedDelay = 4320 * 10000)
    private void scheduleStart() {
        log.info("Parser started");
        List<String> listUrlsToParse = Utils.getListUrlsToParse();
        listUrlsToParse.forEach(this::startParseCatalog);
        log.info("parser finished");
    }

    @Override
    public void startParseCatalog(String catalogURL) {
        if(!Utils.urlIsValid(catalogURL)){
            throw new URLNotValidException(catalogURL);
        }

        try {
            Document docCatalogURL = Jsoup.connect(catalogURL).get();

            List<String> listCatalogToParse = getListCatalogToParse(docCatalogURL);

            List<String> listCategoryToParse = getListCategoryToParse(listCatalogToParse);

            List<String> listProductsToParse = getListProductsToParse(listCategoryToParse);

            parseProducts(listProductsToParse);
        } catch (IOException e) {
            log.warn("Can't parse URL {}", catalogURL);
        }
    }

    @Override
    public List<String> getListCatalogToParse(Document listCatalog) {
        List<String> catalogURLs = new ArrayList<>();
        Elements elements = listCatalog.select("div.item").select("h2");
        for (Element element : elements.select("a")) {
            catalogURLs.add("http://free-run.kiev.ua/" + element.attr("href"));
        }
        if(catalogURLs.isEmpty()){
            catalogURLs.add(listCatalog.location());
            return catalogURLs;
        }
        return catalogURLs;
    }

    @Override
    public List<String> getListCategoryToParse(List<String> listCatalogToParse) {
        List<String> listCategoryToParse = new ArrayList<>();
        for (String catalogURL : listCatalogToParse) {
            try {
                Document doc = Jsoup.connect(catalogURL).get();
                Elements elements = doc.select("div.oPager");
                listCategoryToParse.add(doc.location());
                for (Element element : elements.select("a")) {
                    listCategoryToParse.add("http://free-run.kiev.ua/" + element.attr("href"));
                }
            } catch (IOException e) {
                log.error("IOException when getListCategoryToParse - {}", catalogURL);
            }
        }
        return listCategoryToParse;
    }

    @Override
    public List<String> getListProductsToParse(List<String> listCategoryToParse) {
        List<String> listProductsToParse = new ArrayList<>();

        for(String cat : listCategoryToParse){
            try {
                Document doc = Jsoup.connect(cat).get();
                Elements elements = doc.select("div.items");
                for (Element element : elements.select("a.button")) {
                    listProductsToParse.add("http://free-run.kiev.ua/" + element.attr("href"));
                }
            } catch (IOException e) {
                log.error("IOException when getListProductsToParse - {}", cat);
            }
        }
        return listProductsToParse;
    }

    @Override
    public void parseProducts(List<String> listProductsToParse) {
        List<Integer> listNewProductsToParse = new ArrayList<>(); //todo add parse new products
        for (String productURL : listProductsToParse) {
            int frProductId = parsProduct.getProductIdFromUrl(productURL);
            List<Integer> listMyProductId = productDAO.getMyIdByFrId(frProductId);
            if (listMyProductId.isEmpty()) {
                listNewProductsToParse.add(frProductId);
            } else {
                updateProductOptions(productURL, listMyProductId);
            }
        }
    }

    private void updateProductOptions(String productURL, List<Integer> listMyProductIdToUpdate){

        try {
            Document doc = Jsoup.connect(productURL).get();
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
                        try {
                            int productOptionId = productDAO.getProductOptionId(productId, optionId);

                            for (Integer optionValueId : listOptionsValuesIds) {
                                productDAO.updateOptions(productId, productOptionId, optionId, optionValueId);
                            }
                        } catch (EmptyResultDataAccessException e) {
                            log.error("EmptyResultDataAccessException - {}", productId);
                        }
                    }
                }
            }
        } catch (IOException e) {
            log.warn("Can't connect to product url - {}", productURL);
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