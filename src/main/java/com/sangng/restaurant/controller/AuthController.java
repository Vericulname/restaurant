package com.sangng.restaurant.controller;

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

import com.sangng.restaurant.request.LoginRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.respone.AuthRespone;
import com.sangng.restaurant.security.JWT.JWTUtils;
import com.sangng.restaurant.security.user.BillUserDetail;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
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

}
