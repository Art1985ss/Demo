package com.art.demo.model.mapper;

import com.art.demo.model.Address;
import com.art.demo.model.dto.AddressDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddressMapperTest {
    private static final String TEST_COUNTRY = "TestCountry";
    private static final String TEST_CITY = "TestCity";
    private static final String TEST_STREET = "TestStreet";
    private static final int ID = 1;
    private static final int HOUSE_NUMBER = 2;
    private static final int APARTMENT_NUMBER = 3;
    private static final Address TEST_ADDRESS = createAddress();
    private static final AddressDto TEST_ADDRESS_DTO = getAddressDto();

    @Test
    void fromDto() {
        final Address address = AddressMapper.fromDto(TEST_ADDRESS_DTO);
        assertAll(
                () -> assertEquals(ID, address.getId()),
                () -> assertEquals(TEST_COUNTRY, address.getCountry()),
                () -> assertEquals(TEST_CITY, address.getCity()),
                () -> assertEquals(TEST_STREET, address.getStreet()),
                () -> assertEquals(HOUSE_NUMBER, address.getHouseNumber()),
                () -> assertEquals(APARTMENT_NUMBER, address.getApartmentNumber())
        );
    }

    @Test
    void toDto() {
        final AddressDto addressDto = AddressMapper.toDto(TEST_ADDRESS);
        assertAll(
                () -> assertEquals(ID, addressDto.getId()),
                () -> assertEquals(TEST_COUNTRY, addressDto.getCountry()),
                () -> assertEquals(TEST_CITY, addressDto.getCity()),
                () -> assertEquals(TEST_STREET, addressDto.getStreet()),
                () -> assertEquals(HOUSE_NUMBER, addressDto.getHouseNumber()),
                () -> assertEquals(APARTMENT_NUMBER, addressDto.getApartmentNumber())
        );
    }

    public static Address createAddress() {
        return new Address()
                .setId(ID)
                .setCountry(TEST_COUNTRY)
                .setCity(TEST_CITY)
                .setStreet(TEST_STREET)
                .setHouseNumber(HOUSE_NUMBER)
                .setApartmentNumber(APARTMENT_NUMBER);
    }

    public static AddressDto getAddressDto() {
        return new AddressDto()
                .setId(ID)
                .setCountry(TEST_COUNTRY)
                .setCity(TEST_CITY)
                .setStreet(TEST_STREET)
                .setHouseNumber(HOUSE_NUMBER)
                .setApartmentNumber(APARTMENT_NUMBER);
    }
}