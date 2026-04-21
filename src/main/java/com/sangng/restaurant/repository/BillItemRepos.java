package com.sangng.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.BillItem;

@Repository
public interface BillItemRepos extends JpaRepository<BillItem, Long> {

    void deleteByBillId(Long id);

}
