package com.sangng.restaurant.dto;

import java.util.Set;

import com.sangng.restaurant.model.BillItem;

import lombok.Data;

@Data
public class BillDto {

    private Long id;
    private double totalprice;
    private Set<BillItemDto> billItems;
}
