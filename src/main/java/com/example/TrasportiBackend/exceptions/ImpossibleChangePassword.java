package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class ImpossibleChangePassword extends RuntimeException{
private String message;
private List<ObjectError> messages;
public ImpossibleChangePassword(String message){
    this.message=message;
}
public ImpossibleChangePassword(List<ObjectError> list){
    this.messages=list;
}
}
