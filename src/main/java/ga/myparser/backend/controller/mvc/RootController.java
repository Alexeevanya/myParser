package ga.myparser.backend.controller.mvc;

import ga.myparser.backend.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RootController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/PoolParty/", method = RequestMethod.POST)
    public String startUpdatePoolParty(Model model){
        log.info("Custom update products PoolParty start");
        commonService.updateSneakersPoolParty();
        log.info("Custom update products PoolParty finish");
        model.addAttribute("statusPP", "Update PoolParty completed");
        return "index";
    }

    @RequestMapping(value = "/FreeRun/", method = RequestMethod.POST)
    public String startUpdateFreeRun(Model model){
        log.info("Custom update products FreeRun start");
        commonService.updateSneakersFreeRun();
        log.info("Custom update products FreeRun finish");
        model.addAttribute("statusFR", "Update FreeRun completed");
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
