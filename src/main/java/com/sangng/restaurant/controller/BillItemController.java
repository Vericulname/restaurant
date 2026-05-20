package com.sangng.restaurant.controller;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.sangng.restaurant.model.BillItem;
import com.sangng.restaurant.request.BillItemCreateRequest;
import com.sangng.restaurant.request.BillItemUpdateRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.billitem.IBillItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/bills")
@RequiredArgsConstructor
public class BillItemController {

    private final IBillItemService billItemService;

    @PostMapping("/{billid}/items")
    public ResponseEntity<ApiRespone> createBillItem(@PathVariable("billid") Long billId,
            @RequestBody BillItemCreateRequest request) {
  
            billItemService.AddBillItemToBill(billId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiRespone("BillItem created successfully"));

       
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<ApiRespone> updateBillItem(@PathVariable("id") Long id,
            @RequestBody BillItemUpdateRequest request) {
   
            BillItem updatedBillItem = billItemService.updateBillItem(id, request);
            return ResponseEntity.ok(new ApiRespone("BillItem updated successfully", updatedBillItem));
       

    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<ApiRespone> deleteBillItem(@PathVariable("id") Long id) {

            billItemService.deleteBillItem(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiRespone("BillItem deleted successfully"));
        

    }
}
