package com.sangng.restaurant.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sangng.restaurant.dto.UserDto;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.request.UserCreateRequest;
import com.sangng.restaurant.request.UserUpdateRequest;
import com.sangng.restaurant.respone.ApiRespone;
import com.sangng.restaurant.service.User.IUserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @GetMapping("/getall")
    public ResponseEntity<ApiRespone> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserDto> userDtos = userService.convertListToDtos(users);
        return ResponseEntity.ok(new ApiRespone("Users retrieved successfully", userDtos));
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ApiRespone> getUsersById(@PathVariable("id") Long id) {
        try {
            User user = userService.getUserById(id);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiRespone("User retrieved successfully", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiRespone(e.getMessage(), null));
        }
    }

    @GetMapping("/getbyname/{name}")
    public ResponseEntity<ApiRespone> getUsersByName(@PathVariable("name") String name) {
        try {
            User user = userService.getUserByName(name);
            UserDto userDto = userService.convertToDto(user);
            return ResponseEntity.ok(new ApiRespone("User retrieved successfully", userDto));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiRespone(e.getMessage(), null));
        }
     
    }

    @PostMapping("/create")
    public ResponseEntity<ApiRespone> createUser(@RequestBody UserCreateRequest request) {

        User createdUser = userService.createUser(request);
        UserDto createdUserDto = userService.convertToDto(createdUser);
        return ResponseEntity.ok(new ApiRespone("User created successfully", createdUserDto));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiRespone> updateUser(@PathVariable("id") Long id, @RequestBody UserUpdateRequest request) {
        try {
            User updatedUser = userService.updateUser(id, request);
            UserDto updatedUserDto = userService.convertToDto(updatedUser);
            return ResponseEntity.ok(new ApiRespone("User updated successfully", updatedUserDto));
        } 
        catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiRespone(e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiRespone> deleteUser(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiRespone("User deleted successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ApiRespone(e.getMessage(), null));
        }
        
    }
}
