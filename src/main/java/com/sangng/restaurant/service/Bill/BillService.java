package com.sangng.restaurant.service.Bill;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sangng.restaurant.dto.BillDto;
import com.sangng.restaurant.dto.BillItemDto;
import com.sangng.restaurant.dto.DishDto;
import com.sangng.restaurant.dto.ImageDto;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.model.BillItem;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.model.Image;
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
    private final ModelMapper modelmapper;

    private final BillItemRepos billItemRepos;
    @Override
    public Bill createBill(User user) {

        Bill bill = new Bill();
        bill.setUser(user);
        return billrepos.save(bill);
    }

    @Override
    public List<Bill> getAllBills(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        return billrepos.findAll(sort);
    }
       @Override
       public List<Bill> getBillsByUserId(Long userId, String sortBy, String sortDir) {
            userRepos.findById(userId)
                   .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId ));
            List<Bill> bills;
            Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            bills = billrepos.findByUserId(userId, sort);
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

    @Override
    public BillDto convertToDto(Bill bill) {
        BillDto billDto = modelmapper.map(bill, BillDto.class);
        
        Set<BillItem> billItems = bill.getBillItems();
        Set<BillItemDto> billItemDtos = billItems.stream()
                .map(billItem -> {
                    BillItemDto billItemDto = modelmapper.map(billItem, BillItemDto.class);
                    Dish dish = billItem.getDish();
                    List<Image> images = dish.getImages();
                    
                    List<ImageDto> imageDtos = images.stream()
                            .map(image -> modelmapper.map(image, ImageDto.class))
                            .toList();

                    DishDto dishDto = modelmapper.map(dish, DishDto.class);
                    dishDto.setImagedtos(imageDtos);

                    billItemDto.setDishdto(dishDto);
                    return billItemDto;
                }).collect(Collectors.toSet());

        billDto.setBillItems(billItemDtos);
        return billDto;
    }

 

    @Override
    public List<BillDto> convertListToDtos(List<Bill> bills) {

        return bills.stream().map(this::convertToDto).toList();
    }
    
    

    
}
