package com.art.demo.model.mapper;

import com.art.demo.model.User;
import com.art.demo.model.dto.UserDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    public static User fromDto(final UserDto userDto) {
        if (userDto == null) return null;
        return new User()
                .setId(userDto.getId())
                .setUsername(userDto.getUsername())
                .setPassword(userDto.getPassword())
                .setAge(userDto.getAge())
                .setFirstName(userDto.getFirstName())
                .setLastName(userDto.getLastName())
                .setAuthorities(userDto.getAuthorities())
                .setAddress(AddressMapper.fromDto(userDto.getAddress()));
    }

    public static UserDto toDto(final User user) {
        if (user == null) return null;
        return new UserDto()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setAge(user.getAge())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setAddress(AddressMapper.toDto(user.getAddress()));
    }
}
