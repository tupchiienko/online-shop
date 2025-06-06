package org.cursor.shopservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.cursor.data.dto.UserDto;
import org.cursor.data.model.User;
import org.cursor.shopservice.repository.UserRepository;
import org.cursor.shopservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository repository;

    private UserServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl();
        ReflectionTestUtils.setField(service, "userRepository", repository);
        ReflectionTestUtils.setField(service, "mapper", new ObjectMapper());
    }

    @Test
    void getUserById_returnsUser() {
        UUID id = UUID.randomUUID();
        User user = new User();
        when(repository.findById(id)).thenReturn(Optional.of(user));

        User result = service.getUserById(id);

        assertThat(result).isSameAs(user);
    }

    @Test
    void getAllUsers_returnsList() {
        List<User> list = List.of(new User());
        when(repository.findAll(any(Sort.class))).thenReturn(list);

        List<User> result = service.getAllUsers();

        assertThat(result).isEqualTo(list);
    }

    @Test
    void createUser_savesMappedEntity() {
        UserDto dto = new UserDto();
        dto.setFirstName("f");
        User saved = new User();
        when(repository.save(any(User.class))).thenReturn(saved);

        User result = service.createUser(dto);

        assertThat(result).isSameAs(saved);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getFirstName()).isEqualTo("f");
    }

    @Test
    void updateUser_updatesExisting() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.of(new User()));
        UserDto dto = new UserDto();
        dto.setFirstName("u");

        service.updateUser(id, dto);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(id);
        assertThat(captor.getValue().getFirstName()).isEqualTo("u");
    }

    @Test
    void deleteUser_deletesExisting() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.of(new User()));

        service.deleteUser(id);

        verify(repository).deleteById(id);
    }
}
