package me.hyunggeun.springbootdeveloper.Exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 이메일 중복 예외 처리
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String handleEmailAlreadyExists(EmailAlreadyExistsException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        return "redirect:/signup";  // 회원가입 페이지로 리다이렉트
    }

    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(Exception ex){

        return "errorPage";
    }

}
