package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class TypeMismatchException extends RuntimeException{
    private String error;
    private List<ObjectError> errorList;

    public TypeMismatchException(String message){
        this.error=message;
    }
    public TypeMismatchException(List<ObjectError> errorList){
        this.errorList= errorList;
    }
}
