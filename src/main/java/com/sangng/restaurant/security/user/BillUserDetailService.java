package com.sangng.restaurant.security.user;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sangng.restaurant.model.User;
import com.sangng.restaurant.repository.UserRepos;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BillUserDetailService implements UserDetailsService {
    private final UserRepos userRepos;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =  Optional.ofNullable(userRepos.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        return BillUserDetail.build(user);
    }

   

}
