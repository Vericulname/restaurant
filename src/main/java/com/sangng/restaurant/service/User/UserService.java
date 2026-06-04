package com.sangng.restaurant.service.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sangng.restaurant.dto.UserDto;
import com.sangng.restaurant.exception.AlreadyexistsException;
import com.sangng.restaurant.exception.ResourceNotFoundException;
import com.sangng.restaurant.initiate.RoleRepos;
import com.sangng.restaurant.model.Roles;
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
    private final PasswordEncoder passwordEncoder;
    private final RoleRepos roleRepos;

    @Override
    public List<User> getUserByName(String name) {
        return Optional.ofNullable(userRepos.findByNameContaining(name))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with name: " + name));
    }

    @Override
    public List<User> getAllUsers(String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
         Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        
        return userRepos.findAll(sort);
    }
   

    @Override
    public User getUserById(Long id) {

        return userRepos.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("user not found with id: " + id));
    }

    @Override
    public User createUser(UserCreateRequest request) {
        Collection<Roles> roles = roleRepos.findAllById(request.getRoleIds());
        if (roles.isEmpty()) {
            roles.add(roleRepos.findByName("ROLE_USER")
                    .orElseThrow(() -> new ResourceNotFoundException("Role not found")));
        }
        
        return Optional.of(request)
                .filter(u -> !userRepos.existsByEmail(u.getEmail()))
                .map(u -> {
                    User user = new User();
                    user.setName(u.getName());
                    user.setEmail(u.getEmail());
                    user.setPassword(passwordEncoder.encode(u.getPassword()));
                    user.setRoles(roles);
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
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
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

    @Override
    public User getAuthedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepos.findByEmail(email); 
    }

}
