package com.sangng.restaurant.request;


import lombok.Data;

@Data
public class BillItemCreateRequest {
    private int quantity;

    private Long dishid;
}
