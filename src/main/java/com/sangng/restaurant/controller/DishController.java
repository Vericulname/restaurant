package com.sangng.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangng.restaurant.dto.DishDto;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.request.DishCreateRequest;
import com.sangng.restaurant.request.DishUpdateRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.Dish.IDishService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/dishes")
public class DishController {

    private final IDishService dishService;

   
    @GetMapping
    public ResponseEntity<ApiRespone> getDishes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double price,
            @RequestParam(defaultValue = "id", required = false) String sortBy,
            @RequestParam(defaultValue = "asc", required = false) String sortDir) {

        List<Dish> dishes;
        if (name != null) {
            dishes = dishService.getDishesByName(name);
        } else if (price != null) {
            dishes = dishService.getDishesByPrice(price);
        } else {
            dishes = dishService.getAllDishes(sortBy, sortDir);
        }

        List<DishDto> dishDtos = dishService.convertListToDtos(dishes);
        return ResponseEntity.ok(new ApiRespone("Dishes retrieved successfully", dishDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiRespone> getDishById(@PathVariable("id") Long id) {

        Dish dish = dishService.getDishById(id);
        DishDto dishDto = dishService.convertToDto(dish);
        return ResponseEntity.ok(new ApiRespone("Dish retrieved successfully", dishDto));

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespone> deleteDish(@PathVariable("id") Long id) {

        dishService.deleteDish(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiRespone("Dish deleted successfully"));

    }

       @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiRespone> createDish(@RequestBody DishCreateRequest request) {
   
            Dish dish = dishService.createDish(request);
            DishDto dishDto = dishService.convertToDto(dish);

            return ResponseEntity.status(HttpStatus.CREATED).
            body(new ApiRespone("Dish created successfully", dishDto));
        

    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiRespone> updateDish(@PathVariable("id") Long id, @RequestBody DishUpdateRequest request) {
  
            Dish dish = dishService.updateDish(id, request);
            DishDto dishDto = dishService.convertToDto(dish);

            return ResponseEntity.ok(new ApiRespone("Dish updated successfully", dishDto));
        

    }

}
