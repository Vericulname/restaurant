package com.sangng.restaurant.service.Image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sangng.restaurant.dto.ImageDto;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Dish;
import com.sangng.restaurant.model.Image;
import com.sangng.restaurant.repository.ImageRepos;
import com.sangng.restaurant.service.Dish.DishService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepos imageRepos;
    private final DishService dishService;
    @Override
    public List<ImageDto> uploadImage(List<MultipartFile> files, Long dishId) {
        Dish dish = dishService.getDishById(dishId);
        List<ImageDto> imageDtos = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                
                String url = "api/v1/images/download/" ;
                String downloadUrl = url + image.getId();
                image.setUrl(downloadUrl);
                image.setDish(dish);
                
                Image savedImage = imageRepos.save(image);
                savedImage.setUrl(url + savedImage.getId());
                imageRepos.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setFileType(savedImage.getFileType());
                imageDto.setUrl(savedImage.getUrl());
                
                imageDtos.add(imageDto);

            } catch ( SQLException | IOException e) {
                throw new RuntimeException("Failed to upload image", e);
            }
        }
        return imageDtos;
    }   

    @Override
    public Image updateImage(Long id, MultipartFile file) {
        Image existingImage = imageRepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
                
            try {
                existingImage.setFileName(file.getOriginalFilename());
                existingImage.setFileType(file.getContentType());
                existingImage.setImage(new SerialBlob(file.getBytes()));
                String url = "api/v1/images/download/" ;
                String downloadUrl = url + existingImage.getId();
                existingImage.setUrl(downloadUrl);
            } catch (SQLException | IOException   e) {
                throw new RuntimeException("Failed to update image", e);
            } 
        
        return imageRepos.save(existingImage);
    }
    
    @Override
    public Image getImageById(Long id) {
       return imageRepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
    }

    @Override
    public void deleteImage(Long id) {
        imageRepos.findById(id).ifPresentOrElse(imageRepos::delete, () -> {
            throw new ResourceNotFoundException("Image not found");
        });
    }


}
