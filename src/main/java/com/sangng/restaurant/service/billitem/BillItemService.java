package com.sangng.restaurant.service.billitem;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Bill;
import com.sangng.restaurant.model.BillItem;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.repository.BillItemRepos;
import com.sangng.restaurant.repository.BillRepos;
import com.sangng.restaurant.request.BillItemCreateRequest;
import com.sangng.restaurant.request.BillItemUpdateRequest;
import com.sangng.restaurant.service.Bill.IBillService;
import com.sangng.restaurant.service.Dish.IDishService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillItemService implements IBillItemService {

    private final BillItemRepos billItemRepos;
    private final BillRepos billRepos;
    private final IBillService billService;
    private final IDishService dishService;

    @Override
    public void AddBillItemToBill(Long billId, BillItemCreateRequest request) {
        try {
            Bill bill = billService.getBillById(billId);
            BillItem item = bill.getBillItems().
                    stream().filter(i -> i.getDish().getId().equals(request.getDishid()))
                    .findFirst()
                    .orElse(new BillItem());

            if (item.getId() != null) {
                item.setQuantity(request.getQuantity());
                item.setTotalPrice();
            } else {
                item = createBillItem(item, request.getQuantity(), request.getDishid());
            }

            item.setBill(bill);

            billItemRepos.save(item);

            bill.getBillItems().add(item);
            // bill.setTotalprice(bill.getTotalprice() + item.getTotalprice());
            billRepos.save(bill);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Cannot create BillItem: " + e.getMessage());
        }
    }

    private BillItem createBillItem(BillItem item, int quantity, Long dishId) {
        Dish dish = dishService.getDishById(dishId);
        item.setDish(dish);
        item.setQuantity(quantity);
        item.setTotalPrice();
        return item;

    }

    @Override
    public List<BillItem> getAllBillItemsFrombill(Long billid) {
        Bill bill = billService.getBillById(billid);
        return bill.getBillItems().stream().toList();
    }

    @Override
    public BillItem updateBillItem(Long billItemid, BillItemUpdateRequest request) {
        return billItemRepos.findById(billItemid)
                .map(existingBillItem -> {
                    try {
                        createBillItem(existingBillItem, request.getQuantity(), request.getDishid());
                        return billItemRepos.save(existingBillItem);

                    } catch (Exception e) {
                        throw new ResourceNotFoundException("Cannot update BillItem: " + e.getMessage());
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException("BillItem not found with id: " + billItemid));
    }

    @Override
    public void deleteBillItem(Long id) {
        billItemRepos.findById(id).ifPresentOrElse(billItemRepos::delete, () -> {
            throw new ResourceNotFoundException("BillItem not found with id: " + id);
        });
    }

}
