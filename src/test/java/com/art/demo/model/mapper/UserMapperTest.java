package com.art.demo.model.mapper;

import com.art.demo.model.User;
import com.art.demo.model.dto.UserDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.art.demo.model.Roles.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserMapperTest {
    private static final int ID = 1;
    private static final int AGE = 20;
    private static final String USERNAME = "username";
    private static final String PASSWORD = "pass";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final User TEST_USER = createUser();
    private static final UserDto TEST_USER_DTO = createUserDto();

    @Test
    void fromDto() {
        final User user = UserMapper.fromDto(TEST_USER_DTO);
        assertAll(
                () -> assertEquals(ID, user.getId()),
                () -> assertEquals(AGE, user.getAge()),
                () -> assertEquals(USERNAME, user.getUsername()),
                () -> assertEquals(FIRST_NAME, user.getFirstName()),
                () -> assertEquals(LAST_NAME, user.getLastName()),
                () -> assertEquals(List.of(ROLE_USER), user.getAuthorities())
        );
    }

    @Test
    void toDto() {
        final UserDto userDto = UserMapper.toDto(TEST_USER);
        assertAll(
                () -> assertEquals(ID, userDto.getId()),
                () -> assertEquals(AGE, userDto.getAge()),
                () -> assertEquals(USERNAME, userDto.getUsername()),
                () -> assertEquals(FIRST_NAME, userDto.getFirstName()),
                () -> assertEquals(LAST_NAME, userDto.getLastName()),
                () -> assertNull(userDto.getAuthorities())
        );
    }

    public static User createUser() {
        return new User()
                .setId(ID)
                .setAge(AGE)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setAddress(null)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setAuthorities(List.of(ROLE_USER));
    }

    public static UserDto createUserDto() {
        return new UserDto()
                .setId(ID)
                .setAge(AGE)
                .setUsername(USERNAME)
                .setPassword(PASSWORD)
                .setAddress(null)
                .setFirstName(FIRST_NAME)
                .setLastName(LAST_NAME)
                .setAuthorities(List.of(ROLE_USER));
    }
}