package com.example.TrasportiBackend.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class UserNotFoundException extends RuntimeException {
    private List<ObjectError> errorList;
    private String error;

    public UserNotFoundException(String message){
        this.error=message;
    }
    public UserNotFoundException(List<ObjectError> message){
        this.errorList=message;
    }

}
