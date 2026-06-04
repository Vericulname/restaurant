package com.sangng.restaurant.request;

import java.util.Collection;
import java.util.List;

import com.sangng.restaurant.model.Roles;

import lombok.Data;

@Data
public class UserCreateRequest {
    private String name;
    private String email;
    private String password;
    private List<Long> roleIds;
}
