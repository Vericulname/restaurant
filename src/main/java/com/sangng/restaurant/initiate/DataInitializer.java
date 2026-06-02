package com.sangng.restaurant.initiate;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.sangng.restaurant.model.Roles;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.repository.UserRepos;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Transactional
@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final RoleRepos roleRepos;
    private final UserRepos userRepos;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeRoles(Set.of("ROLE_USER", "ROLE_ADMIN"));
        initializeAdmin();
        initializeUsers();
    }

    private void initializeUsers() {
        Roles userRole = roleRepos.findByName("ROLE_USER").get();
        

            for (int i = 1; i <= 5; i++) {
                if (userRepos.existsByEmail("user" + i + "@example.com")) {
                    continue; 
                }
                User user = new User();
                user.setName("User " + i);
                user.setEmail("user" + i + "@example.com");
                user.setPassword(passwordEncoder.encode("password" + i));
                user.setRoles(Set.of(userRole));
                userRepos.save(user);
            }
        
    }

    private void initializeAdmin() {
        Roles adminRole = roleRepos.findByName("ROLE_ADMIN").get();
        

            for (int i = 1; i <= 2; i++) {
                if (userRepos.existsByEmail("admin" + i + "@example.com")) {
                    continue; 
                }
                User user = new User();
                user.setName("Admin " + i);
                user.setEmail("admin" + i + "@example.com");
                user.setPassword(passwordEncoder.encode("password" + i));
                user.setRoles(Set.of(adminRole));
                userRepos.save(user);
            }
        
    }

    private void initializeRoles(Set<String> roles) {
        if (roleRepos.count() == 0) {
            roles.stream()
                .filter(roleName -> roleRepos.findByName(roleName).isEmpty())
                .map(roleName -> {
                    Roles role = new Roles();
                    role.setName(roleName);
                    return role;
                })
                .forEach(roleRepos::save);
        }
    }

}
