package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.cursor.data.dto.UserDto;
import org.cursor.data.model.User;
import org.cursor.shopservice.repository.UserRepository;
import org.cursor.shopservice.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ObjectMapper mapper;

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName"));
    }

    @Override
    public User createUser(UserDto userDto) {
        return userRepository.save(mapper.convertValue(userDto, User.class));
    }

    @Override
    public void updateUser(UUID id, UserDto userDto) {
        userRepository.findById(id).orElseThrow();
        var user = mapper.convertValue(userDto, User.class);
        user.setId(id);
        userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.findById(id).orElseThrow();
        userRepository.deleteById(id);
    }
}
