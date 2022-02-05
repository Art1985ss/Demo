package com.art.demo.service;

import com.art.demo.config.WebSecurityConfig;
import com.art.demo.model.Address;
import com.art.demo.model.User;
import com.art.demo.model.dto.AddressDto;
import com.art.demo.repository.AddressRepository;
import com.art.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.art.demo.model.mapper.AddressMapper.fromDto;
import static com.art.demo.model.mapper.AddressMapper.toDto;

@Service
public class AddressService implements CRUD<AddressDto> {
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Autowired
    public AddressService(final AddressRepository addressRepository, final UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AddressDto create(final AddressDto addressDto) {
        final User user = (User) WebSecurityConfig.getAuthentication().getPrincipal();
        userRepository.save(user);
        return toDto(user.getAddress());
    }

    @Override
    public void update(final AddressDto addressDto, final long id) {
        final Address address = fromDto(addressDto).setId(id);
        addressRepository.save(address);
    }

    @Override
    public AddressDto findById(final long id) {
        return null;
    }

    @Override
    public List<AddressDto> findAll() {
        return null;
    }

    @Override
    public void delete(final AddressDto addressDto) {

    }

    @Override
    public void deleteById(final long id) {

    }
}
