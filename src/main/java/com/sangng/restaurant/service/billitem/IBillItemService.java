package com.sangng.restaurant.service.billitem;

import java.util.List;

import com.sangng.restaurant.model.BillItem;
import com.sangng.restaurant.request.BillItemCreateRequest;
import com.sangng.restaurant.request.BillItemUpdateRequest;

public interface IBillItemService {
    void AddBillItemToBill(Long billId, BillItemCreateRequest request);

    List<BillItem> getAllBillItemsFrombill(Long billid);

    BillItem updateBillItem(Long id, BillItemUpdateRequest request);
    void deleteBillItem(Long id);

  
}
