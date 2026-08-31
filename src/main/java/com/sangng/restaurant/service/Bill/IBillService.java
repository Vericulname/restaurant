package com.sangng.restaurant.service.Bill;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sangng.restaurant.dto.BillDto;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.model.User;

public interface IBillService {

    Bill createBill(User user);
    Bill getBillById(Long id);
    void deleteBill(Long id);

    void clearBillItems(Long id);

    Page<Bill> getAllBills(Pageable pageable);

    Page<Bill> getBillsByTotalPrice(double totalPrice, Pageable pageable);
    Page<Bill> getBillsByUserId(Long userId, Pageable pageable);

    BillDto convertToDto(Bill bill);

    List<BillDto> convertListToDtos(List<Bill> bills);
    

}
