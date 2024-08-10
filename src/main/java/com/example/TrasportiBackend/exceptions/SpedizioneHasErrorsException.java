package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class SpedizioneHasErrorsException extends RuntimeException{
    private String error;
    private List<ObjectError> errorList;

    public SpedizioneHasErrorsException(String message){
        this.error=message;
    }

    public SpedizioneHasErrorsException(List<ObjectError> errorList){
        this.errorList=errorList;
    }
}
