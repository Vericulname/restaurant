package com.sangng.restaurant.service.Image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sangng.restaurant.dto.ImageDto;
import com.sangng.restaurant.model.Image;

public interface IImageService {
    List<ImageDto> uploadImage(List<MultipartFile> files, Long dishId);

    Image getImageById(Long id);

    void deleteImage(Long id);
    Image updateImage(Long id, MultipartFile files);
}
