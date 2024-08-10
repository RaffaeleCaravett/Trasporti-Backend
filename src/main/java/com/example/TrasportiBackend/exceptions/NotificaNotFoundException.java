package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificaNotFoundException extends RuntimeException{
    private String error;

    public NotificaNotFoundException(String message){
        this.error=message;
    }
}
