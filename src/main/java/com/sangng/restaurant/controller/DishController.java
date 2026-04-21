package com.sangng.restaurant.controller;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sangng.restaurant.dto.DishDto;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.request.DishCreateRequest;
import com.sangng.restaurant.request.DishUpdateRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.Dish.IDishService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/dishes")
public class DishController {
    private final IDishService dishService;

    @GetMapping("/getall")
    public ResponseEntity<ApiRespone> getAllDishes() {
        List<Dish> dishes = dishService.getAllDishes();
        List<DishDto> dishDtos = dishService.convertListToDtos(dishes);
        return ResponseEntity.ok(new ApiRespone("Dishes retrieved successfully", dishDtos));
    }
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApiRespone> getDishById(@PathVariable("id") Long id) {
        try{
        Dish dish = dishService.getDishById(id);
    DishDto dishDto = dishService.convertToDto(dish);
        return ResponseEntity.ok(new ApiRespone("Dish retrieved successfully", dishDto));
        }catch (Exception e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage(), null));
        }
    }
    @GetMapping("/getbyname/{name}")
    public ResponseEntity<ApiRespone> getDishByName(@PathVariable("name") String name) {
        try {
            List<Dish> dishes = dishService.getDishesByName(name);
            List<DishDto> dishDtos = dishService.convertListToDtos(dishes);
            return ResponseEntity.ok(new ApiRespone("Dishes retrieved successfully", dishDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage(), null));
        }
       
    }
    
    @GetMapping("/getbyprice/{price}")
    public ResponseEntity<ApiRespone> getDishByPrice(@PathVariable("price") double price) {
        try {
            List<Dish> dishes = dishService.getDishesByPrice(price);
            List<DishDto> dishDtos = dishService.convertListToDtos(dishes);
            return ResponseEntity.ok(new ApiRespone("Dishes retrieved successfully", dishDtos));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage(), null));
        }

    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiRespone> deleteDish(@PathVariable("id") Long id) {
        try {
            dishService.deleteDish(id);
            return ResponseEntity.ok(new ApiRespone("Dish deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage(), null));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiRespone> createDish(@RequestBody DishCreateRequest request) {
        try {
            Dish dish = dishService.createDish(request);
            DishDto dishDto = dishService.convertToDto(dish);

            return ResponseEntity.ok(new ApiRespone("Dish created successfully", dishDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespone(e.getMessage(), null));
        }
        

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiRespone> updateDish(@PathVariable("id") Long id, @RequestBody DishUpdateRequest request) {
        try {
            Dish dish = dishService.updateDish(id, request);
            DishDto dishDto = dishService.convertToDto(dish);

            return ResponseEntity.ok(new ApiRespone("Dish updated successfully", dishDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespone(e.getMessage(), null));
        }
        

    }
    

}
