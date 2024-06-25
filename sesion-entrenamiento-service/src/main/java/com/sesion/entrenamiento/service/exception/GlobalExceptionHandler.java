package com.sesion.entrenamiento.service.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.sesion.entrenamiento.service.controllers.payload.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Data fields JSON/formData
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleInvalidArguments(MethodArgumentNotValidException exception){

        StringBuilder builder;
        builder = new StringBuilder();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            builder.append(error.getDefaultMessage()).append(", ");
        });

        return ApiResponse
            .builder()
            .flag(false)
            .code(400)
            .message(builder.toString())
            .data("Error de validación de campos: " + exception.getCause())
            .build();

    }


    // Incorrect data type method arguments
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleTypeMismatchException(MethodArgumentTypeMismatchException exception){

        log.warn("Error de tipo de argumento", exception);
        return ApiResponse
            .builder()
            .flag(false)
            .code(400)
            .message("Favor proveer el tipo de argumento correcto")
            .data("Error de tipo de argumento en metodo: " + exception.getMessage())
            .build();

    }


    // Deserialization error
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {

        log.warn("Error de deserialización", exception);
        return ApiResponse
            .builder()
            .flag(false)
            .code(400)
            .message("Error al interpretar los datos")
            .data("Error de deserialización JSON: " + exception.getMessage())
            .build();
        
    }


    // Staff resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handlerResourceNotFoundException(ResourceNotFoundException exception){

        return ApiResponse
            .builder()
            .flag(false)
            .code(400)
            .message(exception.getMessage())
            .data("Elemento no identificado")
            .build();

    }


    // Stadium already exists
    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse handlerResourceAlreadyExistsException(ResourceAlreadyExistsException exception){
        
        return ApiResponse
            .builder()
            .flag(false)
            .code(400)
            .message(exception.getMessage())
            .data("Elemento ya existente")
            .build();

    }


    // 404 errors
    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiResponse handleNoResourceFoundException(NoResourceFoundException exception){
        
        log.warn("Intento de acceso a recurso no identificado");
        return ApiResponse
            .builder()
            .flag(false)
            .code(404)
            .message("Recurso o URL no identificado")
            .data(exception.getMessage())
            .build();

    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse handlerNoHandlerFoundException(NoHandlerFoundException exception) {
        
        log.warn("Acción delicada no manejadad correctamente", exception);
        return ApiResponse
            .builder()
            .flag(false)
            .code(404)
            .message("No fue posible encontrar un manejador adecuado")
            .data(exception.getMessage())
            .build();

    }


    // Server errors
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiResponse handleDataAccessException(DataAccessException exception){

        log.error("Error de servidor de BD", exception);
        return ApiResponse
            .builder()
            .flag(false)
            .code(500)
            .message("Error en persistencia de datos")
            .data("Error de servidor de BD: " + exception.getMessage())
            .build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    ApiResponse handleOtherException(Exception exception){

        log.error("Error interno de servidor", exception);
        return ApiResponse
            .builder()
            .flag(false)
            .code(500)
            .message("Ha ocurrido un error inesperado")
            .data("Error de servidor: " + exception.getMessage())
            .build();

    }

    
}
