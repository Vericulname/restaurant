package com.sangng.restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.User;

@Repository
public interface  UserRepos extends JpaRepository<User, Long> {

    public User findByName(String name);

    public boolean existsByEmail(String email);

}
