package com.art.demo.service;

import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Roles;
import com.art.demo.model.User;
import com.art.demo.model.dto.UserDto;
import com.art.demo.model.mapper.UserMapper;
import com.art.demo.repository.AddressRepository;
import com.art.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.art.demo.model.mapper.UserMapper.fromDto;
import static com.art.demo.model.mapper.UserMapper.toDto;

@Service
public class UserService implements CRUD<UserDto> {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final Validator<User> validator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(final AddressRepository addressRepository, final UserRepository userRepository, final PasswordEncoder passwordEncoder) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.validator = user -> userRepository.findByUsername(user.getUsername()).ifPresent(usr -> {
            throw new ValidationException("User with username " + user.getUsername() + " already exists in database");
        });
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto create(final UserDto userDto) {
        final User user = fromDto(userDto)
                .setAuthorities(List.of(Roles.ROLE_USER))
                .setPassword(passwordEncoder.encode(userDto.getPassword()));
        validator.validate(user);
        addressRepository.save(user.getAddress());
        return toDto(userRepository.save(user));
    }

    @Override
    public void update(final UserDto userDto, final long id) {
        final User user = fromDto(userDto).setId(id);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(getById(user.getId()).getPassword());
        }
        validator.validate(user);
        addressRepository.save(user.getAddress());
        userRepository.save(user);
    }

    @Override
    public UserDto findById(final long id) {
        return toDto(getById(id));
    }

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void delete(final UserDto userDto) {
        userRepository.delete(fromDto(userDto));
    }

    @Override
    public void deleteById(final long id) {
        addressRepository.deleteById(fromDto(this.findById(id)).getAddress().getId());
        userRepository.deleteById(id);
    }

    private User getById(final long id) {
        return userRepository.getById(id);
    }
}
