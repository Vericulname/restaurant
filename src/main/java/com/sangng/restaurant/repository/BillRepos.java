package com.sangng.restaurant.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.Bill;

@Repository
public interface BillRepos extends JpaRepository<Bill, Long> {

    List<Bill> findBytotalprice(double totalPrice);

    List<Bill> findByBillItemsDishName(String dishName);
    
    List<Bill> findByUserId(Long userId,Sort sort);
    


}
