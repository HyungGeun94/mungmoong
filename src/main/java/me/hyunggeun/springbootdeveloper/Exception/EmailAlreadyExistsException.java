package me.hyunggeun.springbootdeveloper.Exception;

public class EmailAlreadyExistsException extends RuntimeException {


    public EmailAlreadyExistsException(String message) {
        super(message);
    }


}
