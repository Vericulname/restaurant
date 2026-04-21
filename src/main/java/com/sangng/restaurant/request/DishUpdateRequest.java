package com.sangng.restaurant.request;

import lombok.Data;

@Data
public class DishUpdateRequest {

    private long id;
    private String name;
    private String description;
    private double price;
}
