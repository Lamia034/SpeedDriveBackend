package com.example.SpeedDriveBackend.exceptions;

public class ResourceUnprocessableException extends RuntimeException {
    public ResourceUnprocessableException(String message) {
        super(message);
    }

}
