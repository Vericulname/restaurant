package com.sangng.restaurant.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sangng.restaurant.dto.UserDto;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.request.UserCreateRequest;
import com.sangng.restaurant.request.UserUpdateRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.User.IUserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") 
public class UserController {

    private final IUserService userService;

    @GetMapping
    public ResponseEntity<ApiRespone> getUsers(
            @RequestParam(required = false) String name,
            Pageable pageable
    ) {
        if (name != null && !name.isEmpty()) {
            Page<User> users = userService.getUserByName(name, pageable);
            Page<UserDto> userDtos = users.map(userService::convertToDto);
            return ResponseEntity.ok(new ApiRespone("User retrieved successfully", userDtos));
        }
        Page<User> users = userService.getAllUsers(pageable);
        Page<UserDto> userDtos = users.map(userService::convertToDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiRespone("Users retrieved successfully", userDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiRespone> getUsersById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        UserDto userDto = userService.convertToDto(user);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiRespone("User retrieved successfully", userDto));
    }

    @PostMapping
    public ResponseEntity<ApiRespone> createUser(@RequestBody UserCreateRequest request) {
        User createdUser = userService.createUser(request);
        UserDto createdUserDto = userService.convertToDto(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiRespone("User created successfully", createdUserDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiRespone> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {
        User updatedUser = userService.updateUser(id, request);
        UserDto updatedUserDto = userService.convertToDto(updatedUser);
        return ResponseEntity.ok(new ApiRespone("User updated successfully", updatedUserDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiRespone> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiRespone("User deleted successfully"));
    }
}
