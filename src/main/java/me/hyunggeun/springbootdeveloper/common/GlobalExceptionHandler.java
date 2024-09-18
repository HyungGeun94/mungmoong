package me.hyunggeun.springbootdeveloper.common;

import me.hyunggeun.springbootdeveloper.user.exception.EmailAlreadyExistsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(Exception ex){

        return "errorPage";
    }

}
