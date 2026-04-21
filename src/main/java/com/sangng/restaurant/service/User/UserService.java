package com.sangng.restaurant.service.User;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.sangng.restaurant.dto.UserDto;
import com.sangng.restaurant.exception.AlreadyexistsException;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.model.User;
import com.sangng.restaurant.repository.UserRepos;
import com.sangng.restaurant.request.UserCreateRequest;
import com.sangng.restaurant.request.UserUpdateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepos userRepos;
    private final ModelMapper modelMapper;

    @Override
    public User getUserByName(String name) {
        return Optional.ofNullable(userRepos.findByName(name))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with name: " + name));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepos.findAll();
    }

    @Override
    public User getUserById(Long id) {

        return userRepos.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));
    }

    @Override
    public User createUser(UserCreateRequest request) {
        
        return Optional.of(request)
                .filter(u -> !userRepos.existsByEmail(u.getEmail()))
                .map(u -> {
                    User user = new User();
                    user.setName(u.getName());
                    user.setEmail(u.getEmail());
                    user.setPassword(u.getPassword());
                    return userRepos.save(user);
                })
                .orElseThrow(() -> new AlreadyexistsException("User already exists with email: " + request.getEmail()));
    }

    @Override
    public User updateUser(Long id, UserUpdateRequest request) {
        return userRepos.findById(id)
                .map(user -> {
                    user.setName(request.getName());
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    return userRepos.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public void deleteUser(Long id) {
        userRepos.findById(id).ifPresentOrElse(userRepos::delete, () -> {
            throw new ResourceNotFoundException("User not found with id: " + id);
        });
    }

    @Override
    public UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> convertListToDtos(List<User> users) {
        return users.stream()
                .map(this::convertToDto)
                .toList();
    }

}
