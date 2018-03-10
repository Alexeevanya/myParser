package ga.myparser.backend.controller.mvc;

import ga.myparser.backend.service.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RootController {

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String index() throws IOException {

        Document listCategory = Jsoup.connect("http://free-run.kiev.ua/catalog/muzhskie-mokasiny-ugg").get();

        List<String> listCategoryToParse = productService.getListCategoryToParse(listCategory);

        ArrayList<String> listProductsToParse = productService.getListProductsToParse(listCategoryToParse);

        ArrayList<String> resultList = productService.saveProducts(listProductsToParse);

        return "index";
    }
}
