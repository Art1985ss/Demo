package com.art.demo.service;

import com.art.demo.model.User;
import com.art.demo.model.mapper.UserMapperTest;
import com.art.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsServiceImplTest {
    private static final User TEST_USER = UserMapperTest.createUser();
    private static final String USERNAME = TEST_USER.getUsername();
    private final UserRepository userRepositoryMock = mock(UserRepository.class);
    private final UserDetailsServiceImpl userDetailsService;

    UserDetailsServiceImplTest() {
        this.userDetailsService = new UserDetailsServiceImpl(userRepositoryMock);
    }

    @Test
    void loadUserByUsername() {
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(Optional.of(TEST_USER));

        final UserDetails actualResponse = userDetailsService.loadUserByUsername(USERNAME);

        verify(userRepositoryMock).findByUsername(USERNAME);
        assertEquals(USERNAME, actualResponse.getUsername());
    }

    @Test
    void loadUserByUsernameUserNotFound() {
        when(userRepositoryMock.findByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, ()-> userDetailsService.loadUserByUsername(USERNAME));

        verify(userRepositoryMock).findByUsername(USERNAME);
    }
}