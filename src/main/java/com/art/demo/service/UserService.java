package com.art.demo.service;

import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.User;
import com.art.demo.model.dto.UserDto;
import com.art.demo.model.mapper.UserMapper;
import com.art.demo.repository.AddressRepository;
import com.art.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.art.demo.model.mapper.UserMapper.fromDto;
import static com.art.demo.model.mapper.UserMapper.toDto;

@Service
public class UserService implements CRUD<UserDto> {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final Validator<User> validator;

    @Autowired
    public UserService(final AddressRepository addressRepository, final UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.validator = user -> userRepository.findByUsername(user.getUsername()).ifPresent(usr -> {
            throw new ValidationException("User with username " + user.getUsername() + " already exists in database");
        });
    }

    @Override
    public UserDto create(final UserDto userDto) {
        final User user = fromDto(userDto);
        validator.validate(user);
        addressRepository.save(user.getAddress());
        return toDto(userRepository.save(user));
    }

    @Override
    public void update(final UserDto userDto, final long id) {
        final User user = fromDto(userDto).setId(id);
        validator.validate(user);
        addressRepository.save(user.getAddress());
        userRepository.save(user);
    }

    @Override
    public UserDto findById(final long id) {
        return toDto(userRepository.getById(id));
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
        addressRepository.deleteById(this.findById(id).getAddress().getId());
        userRepository.deleteById(id);
    }
}
