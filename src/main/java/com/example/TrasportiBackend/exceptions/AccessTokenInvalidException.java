package com.example.TrasportiBackend.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;

public class AccessTokenInvalidException extends RuntimeException{
    private String error;
    private List<ObjectError> errorList;

    public AccessTokenInvalidException(String message){
        this.error=message;
    }
    public AccessTokenInvalidException(List<ObjectError> messageList){
        this.errorList=messageList;
    }
}
