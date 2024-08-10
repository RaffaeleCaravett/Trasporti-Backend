package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotOwnerException extends RuntimeException{
    private String error;

    public NotOwnerException(String message){
        this.error=message;
    }
}
