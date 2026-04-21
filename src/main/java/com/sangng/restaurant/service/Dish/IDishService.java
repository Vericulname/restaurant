package com.sangng.restaurant.service.Dish;

import java.util.List;

import com.sangng.restaurant.dto.DishDto;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.request.DishCreateRequest;
import com.sangng.restaurant.request.DishUpdateRequest;

public interface IDishService {
    List<Dish> getAllDishes();

    Dish getDishById(Long id);
    List<Dish> getDishesByName(String name);
    List<Dish> getDishesByPrice(double price);
    Dish createDish(DishCreateRequest request);
    Dish updateDish(Long id, DishUpdateRequest request);

    void deleteDish(Long id);

    DishDto convertToDto(Dish dish);

    List<DishDto> convertListToDtos(List<Dish> dishes);
    
}
