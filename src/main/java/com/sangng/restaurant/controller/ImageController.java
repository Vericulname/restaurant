package com.sangng.restaurant.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sangng.restaurant.dto.ImageDto;
import com.sangng.restaurant.model.Image;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.Image.IImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService service;

    @PostMapping("/dishes/{dishid}/images")
    public ResponseEntity<ApiRespone> uploadImage(@RequestParam("files") List<MultipartFile> files,
            @PathVariable("dishid") long dishid) {
 

            List<ImageDto> imageDtos = service.uploadImage(files, dishid);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .
            body(new ApiRespone("Images uploaded successfully", imageDtos));
        
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable("id") Long id) throws SQLException {

             Image image = service.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
      
       

    }

    @PutMapping("/images/{id}")
    public ResponseEntity<ApiRespone> updateImage(@PathVariable("id") Long id, @RequestParam("files") MultipartFile file) {
    
        service.updateImage(id, file);
        return ResponseEntity.ok(new ApiRespone("Image updated successfully"));
        
    }

    @DeleteMapping("/images/{id}")
    public ResponseEntity<ApiRespone> deleteImage(@PathVariable("id") Long id) {
            service.deleteImage(id);
            return ResponseEntity.ok(new ApiRespone("Image deleted successfully"));
    }
}
