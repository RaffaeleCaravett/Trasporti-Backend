package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class SpedizioneHasErrorsException extends RuntimeException{
    private String message;
    private List<ObjectError> errorList;

    public SpedizioneHasErrorsException(String message){
        this.message=message;
    }

    public SpedizioneHasErrorsException(List<ObjectError> errorList){
        this.errorList=errorList;
    }
}
