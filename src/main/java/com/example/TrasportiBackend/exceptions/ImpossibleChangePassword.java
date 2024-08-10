package com.example.TrasportiBackend.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class ImpossibleChangePassword extends RuntimeException{
private String error;
private List<ObjectError> errorList;
public ImpossibleChangePassword(String message){
    this.error=message;
}
public ImpossibleChangePassword(List<ObjectError> list){
    this.errorList=list;
}
}
