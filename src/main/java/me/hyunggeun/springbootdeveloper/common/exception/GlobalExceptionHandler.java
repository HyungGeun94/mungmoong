package me.hyunggeun.springbootdeveloper.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(Exception ex){

        return "errorPage";
    }

}
