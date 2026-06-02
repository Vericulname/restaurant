package com.sangng.restaurant.initiate;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sangng.restaurant.model.Roles;

@Repository
public interface RoleRepos extends JpaRepository<Roles, Long> {
    Optional<Roles> findByName(String name);


}
