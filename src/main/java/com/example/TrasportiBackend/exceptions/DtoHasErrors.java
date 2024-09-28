package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class DtoHasErrors extends RuntimeException{
    private String error;
    private List<ObjectError> errorList;

    public DtoHasErrors(String message){
        this.error=message;
    }
    public DtoHasErrors(List<ObjectError> errorList){
        this.errorList=errorList;
    }
}
