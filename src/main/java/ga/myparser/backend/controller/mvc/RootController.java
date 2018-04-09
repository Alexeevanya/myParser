package ga.myparser.backend.controller.mvc;

import ga.myparser.backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class RootController {

    private final ProductService productService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String parseCategory(Model model, @RequestParam String categoryURL){

        productService.startParseCategory(categoryURL);

        model.addAttribute("listProductsToParse", categoryURL);

        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
