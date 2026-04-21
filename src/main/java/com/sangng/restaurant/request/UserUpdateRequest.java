package com.sangng.restaurant.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String email;
    private String password;
}
