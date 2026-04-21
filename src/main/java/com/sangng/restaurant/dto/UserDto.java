package com.sangng.restaurant.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    // private List<BillDto> bills;
}
