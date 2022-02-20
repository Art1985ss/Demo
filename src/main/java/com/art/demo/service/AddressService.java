package com.art.demo.service;

import com.art.demo.config.WebSecurityConfig;
import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Address;
import com.art.demo.model.User;
import com.art.demo.model.dto.AddressDto;
import com.art.demo.model.mapper.AddressMapper;
import com.art.demo.model.mapper.UserMapper;
import com.art.demo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.art.demo.model.mapper.AddressMapper.fromDto;
import static com.art.demo.model.mapper.AddressMapper.toDto;

@Service
public class AddressService implements CRUD<AddressDto> {
    private final AddressRepository addressRepository;
    private final UserService userService;
    private final Validator<Address> validateUser;

    @Autowired
    public AddressService(final AddressRepository addressRepository, final UserService userService) {
        this.addressRepository = addressRepository;
        this.userService = userService;
        validateUser = address -> {
            final String name = WebSecurityConfig.getAuthentication().getName();
            final User user = userService.findByName(name);
            if (user.getAddress() == null || !user.getAddress().equals(address)) {
                throw new ValidationException("Address can only be manipulated by owner!");
            }
        };
    }

    @Override
    public AddressDto create(final AddressDto addressDto) {
        final User user = userService.findByName(WebSecurityConfig.getAuthentication().getName());
        user.setAddress(fromDto(addressDto));
        userService.update(UserMapper.toDto(user), user.getId());
        return toDto(user.getAddress());
    }

    @Override
    public void update(final AddressDto addressDto, final long id) {
        final Address address = fromDto(addressDto).setId(id);
        validateUser.validate(address);
        addressRepository.save(address);
    }

    @Override
    public AddressDto findById(final long id) {
        final Address address = addressRepository.findById(id).orElseThrow(() -> new NoEntityFound("id", Address.class));
        validateUser.validate(address);
        return toDto(address);
    }

    @Override
    public List<AddressDto> findAll() {
        return addressRepository.findAll().stream()
                .map(AddressMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(final long id) {
        final Address address = addressRepository.getById(id);
        validateUser.validate(address);
        addressRepository.deleteById(id);
    }
}
