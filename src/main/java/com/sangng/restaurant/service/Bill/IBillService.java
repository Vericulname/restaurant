package com.sangng.restaurant.service.Bill;

import java.util.List;

import com.sangng.restaurant.dto.BillDto;
import com.sangng.restaurant.model.Bill;

public interface IBillService {
    Bill createBill(Long userid);
    Bill getBillById(Long id);
    void deleteBill(Long id);

    void clearBillItems(Long id);

    List<Bill> getAllBills();

    List<Bill> getBillsByTotalPrice(double totalPrice);
    List<Bill> getBillsByUserId(Long userId);

    // BillDto convertToDto(Bill bill);

    // List<BillDto> convertListToDtos(List<Bill> bills);
    

}
