package com.example.TrasportiBackend.exceptions;

import com.example.TrasportiBackend.payloads.errors.ErrorsDTO;
import com.example.TrasportiBackend.payloads.errors.ErrorsWithListDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsWithListDTO handleBadRequest(BadRequestException e) {
        if (e.getErrorList() != null) {
            List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return new ErrorsWithListDTO(e.getError(), new Date(), errorsList);
        } else {
            return new ErrorsWithListDTO(e.getError(), new Date(), new ArrayList<>());
        }

    }
    @ExceptionHandler(SpedizioneHasErrorsException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)  // 404
    public ErrorsWithListDTO handleSpedizioneHasErrors(SpedizioneHasErrorsException e) {
        if (e.getErrorList() != null) {
            List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return new ErrorsWithListDTO(e.getError(), new Date(), errorsList);
        } else {
            return new ErrorsWithListDTO(e.getError(), new Date(), new ArrayList<>());
        }
    }
    @ExceptionHandler(ImpossibleChangePassword.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)  // 404
    public ErrorsWithListDTO handleSpedizioneHasErrors(ImpossibleChangePassword e) {
        if (e.getErrorList() != null) {
            List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return new ErrorsWithListDTO(e.getError(), new Date(), errorsList);
        } else {
            return new ErrorsWithListDTO(e.getError(), new Date(), new ArrayList<>());
        }
    }
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // 401
    public ErrorsDTO handleUnauthorized(UnauthorizedException e) {
        return new ErrorsDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public ErrorsDTO handleAccessDenied(AccessDeniedException e) {
        return new ErrorsDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ErrorsDTO handleNotFound(UserNotFoundException e) {
        return new ErrorsDTO(e.getError(), new Date());
    }
    @ExceptionHandler(AccessTokenInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorsDTO handleInvalidToken(AccessTokenInvalidException e) {
        return new ErrorsDTO(e.getError(), new Date());
    }
    @ExceptionHandler(PasswordMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)  // 404
    public ErrorsDTO handlePasswordMismatch(PasswordMismatchException e) {
        return new ErrorsDTO(e.getError(), new Date());
    }
    @ExceptionHandler(IdsMismatchException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)  // 404
    public ErrorsDTO handleIdsMismatch(IdsMismatchException e) {
        return new ErrorsDTO(e.getError(), new Date());
    }
    @ExceptionHandler(NotOwnerException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorsDTO handleNotOwner(NotOwnerException e) {
        return new ErrorsDTO(e.getError(), new Date());
    }
    @ExceptionHandler(NotificaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ErrorsDTO handleNotificaNotFound(NotificaNotFoundException e) {
        return new ErrorsDTO(e.getError(), new Date());
    }
    @ExceptionHandler(DtoHasErrors.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsWithListDTO handleNotificaNotFound(DtoHasErrors e) {
        if(e.getErrorList()==null){
            return new ErrorsWithListDTO(e.getError(), new Date(),new ArrayList<>());
        }
        List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new ErrorsWithListDTO(e.getError(), new Date(),errorsList);
    }
    @ExceptionHandler(ReceAlreadyExists.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorsWithListDTO handleNotificaNotFound(ReceAlreadyExists e) {
        if(e.getErrorList()==null){
           return new ErrorsWithListDTO(e.getError(), new Date(),new ArrayList<>());
        }
        List<String> errorsList = e.getErrorList().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return new ErrorsWithListDTO(e.getError(), new Date(),errorsList);
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public ErrorsDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsDTO("Problema lato server...giuro che lo risolveremo presto", new Date());
    }
}
