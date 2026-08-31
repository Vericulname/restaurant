package com.sangng.restaurant.service.Dish;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sangng.restaurant.dto.DishDto;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.request.DishCreateRequest;
import com.sangng.restaurant.request.DishUpdateRequest;

public interface IDishService {
    Page<Dish> getAllDishes(Pageable pageable);

    Dish getDishById(Long id);
    Page<Dish> getDishesByName(String name, Pageable pageable);
    Page<Dish> getDishesByPrice(double price, Pageable pageable);
    Dish createDish(DishCreateRequest request);
    Dish updateDish(Long id, DishUpdateRequest request);

    void deleteDish(Long id);

    DishDto convertToDto(Dish dish);

    List<DishDto> convertListToDtos(List<Dish> dishes);
    
}
