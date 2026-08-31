package com.sangng.restaurant.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.Bill;

@Repository
public interface BillRepos extends JpaRepository<Bill, Long> {

    Page<Bill> findByTotalprice(double totalPrice, Pageable pageable);

    Page<Bill> findByUserId(Long userId, Pageable pageable);

}
