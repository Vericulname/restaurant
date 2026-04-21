package com.sangng.restaurant.dto;


import lombok.Data;

@Data
public class BillItemDto {
  private Long id;
    private int quantity;
    private double totalprice;

    private DishDto dishdto;
}
