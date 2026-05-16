package com.sangng.restaurant.controller;


import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.BillItem;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.billitem.IBillItemService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sangng.restaurant.request.BillItemCreateRequest;
import com.sangng.restaurant.request.BillItemUpdateRequest;

@Controller
@RequestMapping("${api.prefix}/billitems")
@RequiredArgsConstructor
public class BillItemController {

    private final IBillItemService billItemService;

    @PostMapping("/createForbillId/{billid}")
    public ResponseEntity<ApiRespone> createBillItem(@PathVariable("billid") Long billId,
            @RequestBody BillItemCreateRequest request) {
        try {
            billItemService.AddBillItemToBill(billId, request);
            return ResponseEntity.ok(new ApiRespone("BillItem created successfully  "));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiRespone(e.getMessage()));
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiRespone> updateBillItem(@PathVariable("id") Long id,
            @RequestBody BillItemUpdateRequest request) {
        try {
            BillItem updatedBillItem = billItemService.updateBillItem(id, request);
            return ResponseEntity.ok(new ApiRespone("BillItem updated successfully"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone("BillItem not found"));

        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiRespone> deleteBillItem(@PathVariable("id") Long id) {
        try {
            billItemService.deleteBillItem(id);
            return ResponseEntity.ok(new ApiRespone("BillItem deleted successfully,"));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage()));
        }

    }
}
