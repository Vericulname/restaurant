package com.sangng.restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.Dish;

@Repository
public interface DishRepos extends JpaRepository<Dish, Long> {

    Page<Dish> findByNameContaining(String name, Pageable pageable);

    Page<Dish> findByPrice(double price, Pageable pageable);

}
