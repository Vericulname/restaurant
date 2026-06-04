package com.sangng.restaurant.controller;

import java.lang.reflect.Array;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sangng.restaurant.dto.UserDto;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.request.LoginRequest;
import com.sangng.restaurant.request.UserCreateRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.respone.AuthRespone;
import com.sangng.restaurant.security.JWT.JWTUtils;
import com.sangng.restaurant.security.user.BillUserDetail;
import com.sangng.restaurant.service.User.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final IUserService userService;
    private final JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<ApiRespone> authenticateUser(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.GenerateJwtToken(authentication);
            BillUserDetail userDetails = (BillUserDetail) authentication.getPrincipal();
            AuthRespone authRespone = new AuthRespone(jwt, userDetails.getId());

            return ResponseEntity.ok(new ApiRespone("Login successful", authRespone));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiRespone("Invalid email or password", null));
        }
    }
    @PostMapping("/register")
    public ResponseEntity<ApiRespone> registerUser(@RequestBody UserCreateRequest userCreateRequest) {
        try {
            userCreateRequest.setRoleIds(java.util.Arrays.asList(2L)); // Default to ROLE_USER
            User createdUser = userService.createUser(userCreateRequest);
            
            
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userCreateRequest.getEmail(), userCreateRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.GenerateJwtToken(authentication);
            BillUserDetail userDetails = (BillUserDetail) authentication.getPrincipal();
            AuthRespone authRespone = new AuthRespone(jwt, userDetails.getId());

            return ResponseEntity.ok(new ApiRespone("Register successful", authRespone));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
            .body(new ApiRespone("Invalid email or password", null));
        }
    }

}
