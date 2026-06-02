package com.sangng.restaurant.service.Bill;

import java.util.List;

import com.sangng.restaurant.dto.BillDto;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.model.User;

public interface IBillService {

    Bill createBill(User user);
    Bill getBillById(Long id);
    void deleteBill(Long id);

    void clearBillItems(Long id);

    List<Bill> getAllBills(String sortBy, String sortDir);

    List<Bill> getBillsByTotalPrice(double totalPrice);
    List<Bill> getBillsByUserId(Long userId, String sortBy, String sortDir);
  
    BillDto convertToDto(Bill bill);

    List<BillDto> convertListToDtos(List<Bill> bills);
    

}
