package com.example.TrasportiBackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class PasswordMismatchException extends RuntimeException{
    private String error;
    private List<ObjectError> errorList;

public PasswordMismatchException(String message){
    this.error=message;
}
    public PasswordMismatchException(List<ObjectError> messageList){
        this.errorList=messageList;
    }
}
