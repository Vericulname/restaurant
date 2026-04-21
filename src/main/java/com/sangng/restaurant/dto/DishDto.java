package com.sangng.restaurant.dto;

import java.util.List;

import lombok.Data;

@Data
public class DishDto {
private Long id;
    private String name;
    private String description;
    private double price;
    
    private List<ImageDto> imagedtos;
}
