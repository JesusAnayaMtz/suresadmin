package com.adminsures.sures.exception;

public class EntidadYaExisteException extends RuntimeException{
    public EntidadYaExisteException(String mensaje){
        super(mensaje);
    }
}
