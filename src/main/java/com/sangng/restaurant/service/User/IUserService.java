package com.sangng.restaurant.service.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sangng.restaurant.dto.UserDto;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.request.UserCreateRequest;
import com.sangng.restaurant.request.UserUpdateRequest;

public interface IUserService {
    Page<User> getUserByName(String name, Pageable pageable);

    Page<User> getAllUsers(Pageable pageable);

    User getUserById(Long id);
    User updateUser(Long id, UserUpdateRequest request);
    void deleteUser(Long id);

    User createUser(UserCreateRequest request);
    UserDto convertToDto(User user);
    List<UserDto> convertListToDtos(List<User> users);

    User getAuthedUser();
    
}
