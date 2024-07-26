package com.example.TrasportiBackend.exceptions;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;

public class BadRequestException extends RuntimeException{
    private List<ObjectError> errorList;
    private String error;
    public BadRequestException(String message){
        this.error=message;
    }
    public BadRequestException(List<ObjectError> messageList){
        this.errorList=messageList;
    }
}
