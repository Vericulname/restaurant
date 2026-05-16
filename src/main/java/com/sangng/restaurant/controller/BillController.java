package com.sangng.restaurant.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sangng.restaurant.dto.BillDto;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.Bill.IBillService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("${api.prefix}/bills")
@RequiredArgsConstructor
public class BillController {

    private final IBillService billService;

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApiRespone> getBillById(@PathVariable("id") Long id) {
        try {
            Bill bill = billService.getBillById(id);
            BillDto billDto = billService.convertToDto(bill);
            return ResponseEntity.ok(new ApiRespone("Bill retrieved successfully", billDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage()));
        }
    }
    
    @GetMapping("/getbyuserid/{id}")
    public ResponseEntity<ApiRespone> getBillByUserId(@PathVariable("id") Long id,
            @RequestParam(defaultValue = "id")String sortBy, @RequestParam(defaultValue = "asc") String sortDir) {
        try {
            List<Bill> bills = billService.getBillsByUserId(id, sortBy, sortDir);
            List<BillDto> billDtos = billService.convertListToDtos(bills);
            return ResponseEntity.ok(new ApiRespone("Bill retrieved successfully", billDtos));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage()));
        }
    }

    @PostMapping("/create/{userid}")
    public ResponseEntity<ApiRespone> createBill(@PathVariable("userid") Long userid){
        try {
            Bill bill = billService.createBill(userid);
            // BillDto billDto = billService.convertToDto(bill);
            return ResponseEntity.ok(new ApiRespone("Bill created successfully", bill));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespone(e.getMessage()));
        }
    }

    @PutMapping("/ClearBillItem/{id}")
    public ResponseEntity<ApiRespone> ClearBillitems(@PathVariable("id") Long billid) {
        try {
            billService.clearBillItems(billid);
            return ResponseEntity.ok(new ApiRespone("Bill cleared successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespone(e.getMessage()));
        }

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiRespone> deleteBill(@PathVariable("id") Long id) {
        try {
            billService.deleteBill(id);
            return ResponseEntity.ok(new ApiRespone("Bill deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage()));
        }
    }

}
