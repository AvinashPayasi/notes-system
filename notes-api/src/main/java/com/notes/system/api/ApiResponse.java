package com.notes.system.api;

public class ApiResponse<T> {
    private ApiStatus status;
    private String message;
    private T data;

    public ApiResponse(ApiStatus status, String message, T data){
        this.status=status;
        this.message=message;
        this.data=data;
    }

    public ApiStatus getStatus(){
        return status;
    }

    public String getMessage(){
        return message;
    }

    public T getData(){
        return data;
    }
}
