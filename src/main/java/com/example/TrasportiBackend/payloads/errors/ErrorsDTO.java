package com.example.TrasportiBackend.payloads.errors;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ErrorsDTO {
    public String message;
    public Date date;
}
