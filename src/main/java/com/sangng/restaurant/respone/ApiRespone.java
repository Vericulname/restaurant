package com.sangng.restaurant.respone;

import lombok.Data;

@Data
public class ApiRespone {
    private String message;
    private Object data;

    public ApiRespone(String message, Object data) {
        this.message = message;
        this.data = data;
    }
    public ApiRespone(String message) {
        this.message = message;
        this.data = null;
    }
}
