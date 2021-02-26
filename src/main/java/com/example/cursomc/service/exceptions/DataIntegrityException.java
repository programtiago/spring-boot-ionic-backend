package com.example.cursomc.service.exceptions;

public class DataIntegrityException extends RuntimeException{

    private static long serialVersionUID = 1L;

    public DataIntegrityException(String msg)
    {
        super(msg);
    }

    public DataIntegrityException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
