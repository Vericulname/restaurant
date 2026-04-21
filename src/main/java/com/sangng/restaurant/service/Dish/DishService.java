package com.sangng.restaurant.service.Dish;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sangng.restaurant.dto.DishDto;
import com.sangng.restaurant.dto.ImageDto;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.model.Image;
import com.sangng.restaurant.repository.DishRepos;
import com.sangng.restaurant.repository.ImageRepos;
import com.sangng.restaurant.request.DishCreateRequest;
import com.sangng.restaurant.request.DishUpdateRequest;
import com.sangng.restaurant.service.Image.ImageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DishService implements IDishService {
    private final DishRepos dishRepos;
    private final ImageRepos imageRepos;
    private final ModelMapper modelMapper;
    @Override
    public List<Dish> getAllDishes() {
        return dishRepos.findAll();
    }

    @Override
    public Dish getDishById(Long id) {
        return  dishRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
    }

    @Override
    public List<Dish> getDishesByName(String name) {
        return dishRepos.findByName(name);
    }

    @Override
    public List<Dish> getDishesByPrice(double price) {
        return dishRepos.findByPrice(price);
    }
    
    @Override
    public void deleteDish(Long id) {
        dishRepos.findById(id).ifPresentOrElse(dishRepos::delete, () -> {
            throw new ResourceNotFoundException("Dish not found with id:" +id);
        });
    }
    @Override
    public Dish createDish(DishCreateRequest request) {
            return Optional.of(request)
            .map(r -> {
                Dish dish = new Dish();
                dish.setName(r.getName());
                dish.setDescription(r.getDescription());
                dish.setPrice(r.getPrice());
                return dishRepos.save(dish);
            })
            .orElseThrow(() -> new IllegalArgumentException("Invalid dish request"));
    }

    @Override
    public Dish updateDish(Long id, DishUpdateRequest request) {
        return Optional.ofNullable(getDishById(id))
                .map(dish -> {
                    dish.setName(request.getName());
                    dish.setDescription(request.getDescription());
                    dish.setPrice(request.getPrice());
                    return dishRepos.save(dish);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Dish not found"));
    }
    @Override
    public DishDto convertToDto(Dish dish) {
        List<Image> images = imageRepos.findByDishId(dish.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
                    
        DishDto dishDto = modelMapper.map(dish, DishDto.class);
        dishDto.setImagedtos(imageDtos);
        return dishDto;
    }
    @Override
    public List<DishDto> convertListToDtos(List<Dish> dishes) {
        return dishes.stream()
                .map(this::convertToDto)
                .toList();
    }


}
