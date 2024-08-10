package com.example.TrasportiBackend.exceptions;

public class NotificaNotFoundException extends RuntimeException{
    private String error;

    public NotificaNotFoundException(String message){
        this.error=message;
    }
}
