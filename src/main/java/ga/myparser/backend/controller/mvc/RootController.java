package ga.myparser.backend.controller.mvc;

import ga.myparser.backend.service.freeRun.ParsProduct;
import ga.myparser.backend.service.freeRun.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RootController {

    private final ProductService productService;

    private final ParsProduct parsProduct;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String parseCategory(Model model, @RequestParam String catalogURL, @RequestParam Double dollarRate){

        parsProduct.setDollarRate(dollarRate);

        productService.startParseCatalog(catalogURL);

        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
