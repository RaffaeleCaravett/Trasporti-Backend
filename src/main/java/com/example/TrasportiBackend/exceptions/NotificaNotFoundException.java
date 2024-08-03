package com.example.TrasportiBackend.exceptions;

public class NotificaNotFoundException extends RuntimeException{
    private String message;

    public NotificaNotFoundException(String message){
        this.message=message;
    }
}
