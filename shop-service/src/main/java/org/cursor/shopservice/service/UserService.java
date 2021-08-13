package org.cursor.shopservice.service;

import org.cursor.data.dto.UserDto;
import org.cursor.data.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    User getUserById(UUID id);

    List<User> getAllUsers();

    User createUser(UserDto userDto);

    void updateUser(UUID id, UserDto userDto);

    void deleteUser(UUID id);

}
