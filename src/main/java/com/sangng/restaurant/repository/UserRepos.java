package com.sangng.restaurant.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.User;

@Repository
public interface  UserRepos extends JpaRepository<User, Long> {

    public Page<User> findByNameContaining(String name, Pageable pageable);

    public boolean existsByEmail(String email);

    public User findByEmail(String username);

}
