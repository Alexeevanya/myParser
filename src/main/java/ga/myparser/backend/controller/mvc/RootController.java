package ga.myparser.backend.controller.mvc;

import ga.myparser.backend.service.common.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class RootController {

    @Autowired
    CommonService commonService;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String startUpdatePoolParty(Model model){
        commonService.updateSneakersPoolParty();
        model.addAttribute("status", "Update completed");
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }
}
