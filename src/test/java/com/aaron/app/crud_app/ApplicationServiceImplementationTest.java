package com.aaron.app.crud_app;

import com.aaron.app.crud_app.models.User;
import com.aaron.app.crud_app.repository.UserRepo;
import com.aaron.app.crud_app.service.ApplicationServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ApplicationServiceImplementationTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private ApplicationServiceImplementation applicationServiceImplementation;

    @Test
    public void testGetUsers() {
        // Given
        User user1 = new User(1, "John", "Doe", "Engineer");
        User user2 = new User(2, "Jane", "Doe", "Doctor");
        when(userRepo.findAll()).thenReturn(Arrays.asList(user1, user2));

        // When
        List<User> users = applicationServiceImplementation.getUsers();

        // Then
        assertEquals(2, users.size());
        assertEquals("John", users.get(0).getFirstName());
        assertEquals("Jane", users.get(1).getFirstName());
    }

    @Test
    public void testAddUser() {
        // Given
        User user = new User(1, "John", "Doe", "Engineer");
        when(userRepo.save(any(User.class))).thenReturn(user);

        // When
        String result = applicationServiceImplementation.addUser(user);

        // Then
        verify(userRepo, times(1)).save(user);
        assertEquals("user added", result);
    }

    @Test
    public void testUpdateUser() {
        // Given
        User existingUser = new User(1, "John", "Doe", "Engineer");
        User updatedUser = new User(1, "John", "Smith", "Doctor");
        when(userRepo.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepo.save(any(User.class))).thenReturn(updatedUser);

        // When
        String result = applicationServiceImplementation.updateUser(1, updatedUser);

        // Then
        verify(userRepo, times(1)).save(updatedUser);
        assertEquals("user 1 updated", result);
    }

    @Test
    public void testUpdateUser_NotFound() {
        // Given
        User updatedUser = new User(1, "John", "Smith", "Doctor");
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        // When
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            applicationServiceImplementation.updateUser(1, updatedUser);
        });

        // Then
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getReason());
    }

    @Test
    public void testDeleteUser() {
        // Given
        User user = new User(1, "John", "Doe", "Engineer");
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        // When
        String result = applicationServiceImplementation.deleteUser(1);

        // Then
        verify(userRepo, times(1)).delete(user);
        assertEquals("user 1 deleted.", result);
    }

    @Test
    public void testDeleteUser_NotFound() {
        // Given
        when(userRepo.findById(1)).thenReturn(Optional.empty());

        // When
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            applicationServiceImplementation.deleteUser(1);
        });

        // Then
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("User not found", exception.getReason());
    }
}
