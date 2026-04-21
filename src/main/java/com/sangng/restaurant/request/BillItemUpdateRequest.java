package com.sangng.restaurant.request;

import lombok.Data;

@Data
public class BillItemUpdateRequest {
    
    private int quantity;
    private double totalprice;
    private Long dishid;
}
