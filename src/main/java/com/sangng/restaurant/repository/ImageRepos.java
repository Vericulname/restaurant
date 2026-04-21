package com.sangng.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.Image;

@Repository
public interface ImageRepos extends JpaRepository<Image, Long> {

    List<Image> findByDishId(Long id);

}
