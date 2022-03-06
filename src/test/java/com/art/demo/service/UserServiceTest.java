package com.art.demo.service;

import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.User;
import com.art.demo.model.dto.UserDto;
import com.art.demo.model.mapper.AddressMapperTest;
import com.art.demo.model.mapper.UserMapperTest;
import com.art.demo.repository.AddressRepository;
import com.art.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private final static User TEST_USER = UserMapperTest.createUser();
    private final static UserDto TEST_USER_DTO = UserMapperTest.createUserDto();
    private final AddressRepository addressRepositoryMock = mock(AddressRepository.class);
    private final UserRepository userRepositoryMock = mock(UserRepository.class);
    private final PasswordEncoder passwordEncoderMock = mock(PasswordEncoder.class);
    private final UserService userService;

    UserServiceTest() {
        this.userService = new UserService(addressRepositoryMock, userRepositoryMock, passwordEncoderMock);
    }

    @AfterEach
    void tearDown() {
        reset(addressRepositoryMock, userRepositoryMock, passwordEncoderMock);
    }

    @Test
    void create() {
        final String encodedPassword = "encodedPassword";
        final String testUserUsername = TEST_USER.getUsername();
        final ArgumentCaptor<String> passwordCaptor = ArgumentCaptor.forClass(String.class);
        when(userRepositoryMock.findByUsername(testUserUsername)).thenReturn(Optional.empty());
        when(passwordEncoderMock.encode(TEST_USER.getPassword())).thenReturn(encodedPassword);
        when(userRepositoryMock.save(any(User.class))).thenReturn(TEST_USER);

        final UserDto actualResponse = userService.create(TEST_USER_DTO);

        verify(userRepositoryMock).findByUsername(testUserUsername);
        verify(addressRepositoryMock).save(TEST_USER.getAddress());
        verify(userRepositoryMock).save(any(User.class));
        verify(passwordEncoderMock).encode(passwordCaptor.capture());
        assertEquals(testUserUsername, actualResponse.getUsername());
        assertEquals(TEST_USER.getPassword(), passwordCaptor.getValue());
    }

    @Test
    void createWithUserNameAlreadyExists() {
        final String encodedPassword = "encodedPassword";
        final String testUserUsername = TEST_USER.getUsername();
        when(userRepositoryMock.findByUsername(testUserUsername)).thenReturn(Optional.of(TEST_USER));
        when(passwordEncoderMock.encode(TEST_USER.getPassword())).thenReturn(encodedPassword);
        when(userRepositoryMock.save(any(User.class))).thenReturn(TEST_USER);

        assertThrows(ValidationException.class, () -> userService.create(TEST_USER_DTO));

        verify(userRepositoryMock).findByUsername(testUserUsername);
        verifyNoInteractions(addressRepositoryMock);
        verifyNoMoreInteractions(userRepositoryMock);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = "pass")
    void update(final String password) {
        final User user = TEST_USER.setPassword(password);
        final long userId = user.getId();
        when(passwordEncoderMock.encode(password)).thenReturn("encodedPassword");
        when(userRepositoryMock.getById(userId)).thenReturn(user);
        when(userRepositoryMock.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        userService.update(TEST_USER_DTO.setPassword(password), userId);

        verify(addressRepositoryMock).save(any());
        verify(userRepositoryMock).save(any(User.class));
        if (password != null)
            verify(passwordEncoderMock).encode(password);


    }

    @Test
    void findById() {
        final long testUserId = TEST_USER.getId();
        when(userRepositoryMock.getById(testUserId)).thenReturn(TEST_USER);

        final UserDto actualResult = userService.findById(testUserId);

        verify(userRepositoryMock).getById(testUserId);
        assertEquals(TEST_USER.getId(), actualResult.getId());
    }

    @Test
    void findAll() {
        when(userRepositoryMock.findAll()).thenReturn(List.of(TEST_USER));

        final List<UserDto> actualResponse = userService.findAll();

        verify(userRepositoryMock).findAll();
        assertEquals(1, actualResponse.size());
    }

    @Test
    void deleteById() {
        final User user = TEST_USER.setAddress(AddressMapperTest.createAddress());
        when(userRepositoryMock.getById(user.getId())).thenReturn(user);

        userService.deleteById(user.getId());

        verify(addressRepositoryMock).deleteById(user.getAddress().getId());
        verify(userRepositoryMock).deleteById(user.getId());
    }

    @Test
    void findByName() {
        when(userRepositoryMock.findByUsername(TEST_USER.getUsername())).thenReturn(Optional.of(TEST_USER));

        final User actualResponse = userService.findByName(TEST_USER.getUsername());

        verify(userRepositoryMock).findByUsername(TEST_USER.getUsername());
        assertEquals(TEST_USER.getUsername(), actualResponse.getUsername());
        assertEquals(TEST_USER.getId(), TEST_USER.getId());
    }
}