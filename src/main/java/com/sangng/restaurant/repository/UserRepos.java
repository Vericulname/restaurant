package com.sangng.restaurant.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.User;

@Repository
public interface  UserRepos extends JpaRepository<User, Long> {

    public List<User> findByNameContaining(String name);

    public boolean existsByEmail(String email);

    public User findByEmail(String username);

}
