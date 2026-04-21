package com.sangng.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.Dish;

@Repository
public interface DishRepos extends JpaRepository<Dish, Long> {

    List<Dish> findByName(String name);

    List<Dish> findByPrice(double price);

}
