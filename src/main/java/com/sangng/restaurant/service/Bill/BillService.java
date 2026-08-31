package com.sangng.restaurant.service.Bill;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Bill> getAllBills(Pageable pageable) {
        // Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
        // Sort.by(sortBy).ascending()
        // : Sort.by(sortBy).descending();
        return billrepos.findAll(pageable);
    }

    @Override
    public Page<Bill> getBillsByUserId(Long userId, Pageable pageable) {
        userRepos.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        // Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
        // Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        // Pageable pageable = Pageable.ofSize(10).withPage(0);
        Page<Bill> billPage = billrepos.findByUserId(userId, pageable);

        return billPage;

    }

    @Override
    public Page<Bill> getBillsByTotalPrice(double totalPrice, Pageable pageable) {
        return billrepos.findByTotalprice(totalPrice, pageable);
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
