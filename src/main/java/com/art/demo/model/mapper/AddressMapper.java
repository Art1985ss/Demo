package com.art.demo.model.mapper;

import com.art.demo.model.Address;
import com.art.demo.model.dto.AddressDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressMapper {

    public static Address fromDto(final AddressDto addressDto) {
        return new Address()
                .setId(addressDto.getId())
                .setCountry(addressDto.getCountry())
                .setCity(addressDto.getCity())
                .setStreet(addressDto.getStreet())
                .setHouseNumber(addressDto.getHouseNumber())
                .setApartmentNumber(addressDto.getApartmentNumber());
    }

    public static AddressDto toDto(final Address address) {
        return new AddressDto()
                .setId(address.getId())
                .setCountry(address.getCountry())
                .setCity(address.getCity())
                .setStreet(address.getStreet())
                .setHouseNumber(address.getHouseNumber())
                .setApartmentNumber(address.getApartmentNumber());
    }
}
