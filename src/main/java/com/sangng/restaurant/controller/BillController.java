package com.sangng.restaurant.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangng.restaurant.dto.BillDto;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.Bill.IBillService;
import com.sangng.restaurant.service.User.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class BillController {

    private final IBillService billService;
    private final IUserService userService;

    @GetMapping("/bills/{id}")
    public ResponseEntity<ApiRespone> getBillById(@PathVariable("id") Long id) {
        Bill bill = billService.getBillById(id);
        BillDto billDto = billService.convertToDto(bill);
        return ResponseEntity.ok(new ApiRespone("Bill retrieved successfully", billDto));
    }

    @GetMapping("/users/bills")
    public ResponseEntity<ApiRespone> getBillByUserId(
            Pageable pageable                
        ) {
        Long id = userService.getAuthedUser().getId();

        Page<Bill> billPage = billService.getBillsByUserId(id, pageable);
     
        Page<BillDto> billDtoPage = billPage.map(bill -> billService.convertToDto(bill));

        return ResponseEntity.ok(new ApiRespone("Bill retrieved successfully", billDtoPage));
    }

    @PostMapping("/users/bills")
    public ResponseEntity<ApiRespone> createBill(
            // @PathVariable("userid") Long userid
    ) {
        User user = userService.getAuthedUser();
        Bill bill = billService.createBill(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiRespone("Bill created successfully", bill));
    }

    @PutMapping("/users/bills/{id}")
    public ResponseEntity<ApiRespone> clearBillItems(@PathVariable("id") Long billid) {
        billService.clearBillItems(billid);
        return ResponseEntity.ok(new ApiRespone("Bill cleared successfully"));
    }

    @DeleteMapping("/bills/{id}")
    public ResponseEntity<ApiRespone> deleteBill(@PathVariable("id") Long id) {
        billService.deleteBill(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiRespone("Bill deleted successfully"));
    }
}
