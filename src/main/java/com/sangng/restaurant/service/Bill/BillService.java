package com.sangng.restaurant.service.Bill;


import java.util.List;


import org.springframework.stereotype.Service;


import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.repository.BillItemRepos;
import com.sangng.restaurant.repository.BillRepos;
import com.sangng.restaurant.repository.UserRepos;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService {
    private final BillRepos billrepos;
    private final UserRepos userRepos;

    private final BillItemRepos billItemRepos;
    @Override
    public Bill createBill(Long userid) {
        Bill bill = new Bill();
        User user = userRepos.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        bill.setUser(user);
        return billrepos.save(bill);
    }

    @Override
    public List<Bill> getAllBills() {
        return billrepos.findAll();
    }
       @Override
       public List<Bill> getBillsByUserId(Long userId) {
            userRepos.findById(userId)
                   .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId ));
            List<Bill> bills;
            bills = billrepos.findByUserId(userId);
            return bills;
        
    }
    
    @Override
    public List<Bill> getBillsByTotalPrice(double totalPrice) {
        return billrepos.findBytotalprice(totalPrice);
    }
    
    @Override
    public Bill getBillById(Long id) {
        return billrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bill not found")); 
       
    }
    
    @Override
    public void deleteBill(Long id) {
        billrepos.findById(id).ifPresentOrElse(billrepos::delete, () -> {
            throw new ResourceNotFoundException("Bill not found");
        });
    }

    @Override
    public void clearBillItems(Long id) {
        Bill bill = getBillById(id);
        billItemRepos.deleteByBillId(id);
        bill.setTotalprice(0);
        billrepos.save(bill);
    }

    // @Override
    // public BillDto convertToDto(Bill bill) {
    //     BillDto billDto = modelmapper.map(bill, BillDto.class);
    //     return billDto;
    // }

 

    // @Override
    // public List<BillDto> convertListToDtos(List<Bill> bills) {

    //     throw new UnsupportedOperationException("Unimplemented method 'convertListToDtos'");
    // }
    
    

    
}
