package com.kanci.data.model.api;

public class ApiException extends Exception {
    int status;

    public int getStatus() {
        return status;
    }


    public ApiException(int status, String message) {
        super(message);
        this.status = status;
    }
}
