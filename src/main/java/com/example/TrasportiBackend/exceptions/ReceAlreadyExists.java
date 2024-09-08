package com.example.TrasportiBackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceAlreadyExists extends RuntimeException{
    private String error;
    private List<ObjectError> errorList;

    public ReceAlreadyExists(String message){
        this.error=message;
    }
    public ReceAlreadyExists(List<ObjectError> message){
        this.errorList=message;
    }
}
