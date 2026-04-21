package com.sangng.restaurant.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sangng.restaurant.dto.ImageDto;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.Image;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.Image.IImageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("${api.prefix}/Images")
@RequiredArgsConstructor
public class ImageController {

    private final IImageService service;

    @PostMapping("/upload/{dishid}")
    public ResponseEntity<ApiRespone> UploadImage(@RequestParam("files") List<MultipartFile> files,
            @PathVariable("dishid") long dishid) {
        try {

            List<ImageDto> imageDtos = service.uploadImage(files, dishid);
            return ResponseEntity.ok(new ApiRespone("Images uploaded successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiRespone(e.getMessage(), null));
        }
    }

    @GetMapping("/dowload/{id}")
    public ResponseEntity<Resource> DowloadImage(@PathVariable("id") Long id) throws SQLException {

        Image image = service.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(
                image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);

    }

    @PutMapping("Updateimage/{id}")
    public ResponseEntity<ApiRespone> putMethodName(@PathVariable("files") Long id,@RequestParam("files") MultipartFile file) {
        try {
            Image image = service.getImageById(id);
            if (image != null) {
                service.updateImage(id, file);
                return ResponseEntity.ok(new ApiRespone("Image updated successfully", null));
            }

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage(), null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiRespone("Error updating image", null));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiRespone> deleteImage(@PathVariable("id") Long id) {
        try {
            service.deleteImage(id);
            return ResponseEntity.ok(new ApiRespone("Image deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiRespone(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiRespone("Error deleting image", e.getMessage()));
        }

    }
}
