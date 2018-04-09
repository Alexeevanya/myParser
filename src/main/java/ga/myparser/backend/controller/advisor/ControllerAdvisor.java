package ga.myparser.backend.controller.advisor;

import ga.myparser.backend.exception.URLNotValidException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(URLNotValidException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotValid(URLNotValidException exeption){
        ModelAndView mav = new ModelAndView("index");
        mav.addObject("notValidUrl", exeption.getUrl());
        return mav;
    }
}
