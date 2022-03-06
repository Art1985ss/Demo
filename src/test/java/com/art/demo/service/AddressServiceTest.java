package com.art.demo.service;

import com.art.demo.config.WebSecurityConfig;
import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Address;
import com.art.demo.model.User;
import com.art.demo.model.dto.AddressDto;
import com.art.demo.model.dto.UserDto;
import com.art.demo.model.mapper.AddressMapperTest;
import com.art.demo.model.mapper.UserMapperTest;
import com.art.demo.repository.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

class AddressServiceTest {
    private final User TEST_USER = UserMapperTest.createUser();
    private final Address TEST_ADDRESS = AddressMapperTest.createAddress();
    private final AddressDto TEST_ADDRESS_DTO = AddressMapperTest.getAddressDto();

    private final UserService userServiceMock = mock(UserService.class);
    private final AddressRepository addressRepositoryMock = mock(AddressRepository.class);
    private final AddressService addressService;
    private MockedStatic<WebSecurityConfig> webSecurityConfigMockedStatic;

    AddressServiceTest() {
        this.addressService = new AddressService(addressRepositoryMock, userServiceMock);
    }

    @BeforeEach
    void setUp() {
        final Authentication authentication = mock(Authentication.class);
        webSecurityConfigMockedStatic = Mockito.mockStatic(WebSecurityConfig.class);
        webSecurityConfigMockedStatic.when(WebSecurityConfig::getAuthentication).thenReturn(authentication);
        when(authentication.getName()).thenReturn(TEST_USER.getUsername());
    }

    @AfterEach
    void tearDown() {
        reset(userServiceMock, addressRepositoryMock);
        webSecurityConfigMockedStatic.close();
    }

    @Test
    void create() {
        when(userServiceMock.findByName(TEST_USER.getUsername())).thenReturn(TEST_USER);

        final AddressDto addressDto = addressService.create(TEST_ADDRESS_DTO);

        assertEquals(TEST_ADDRESS.getId(), addressDto.getId());
        verify(userServiceMock).findByName(TEST_USER.getUsername());
        verify(userServiceMock).update(any(UserDto.class), eq(TEST_USER.getId()));
    }

    @Test
    void update() {
        TEST_USER.setAddress(TEST_ADDRESS);
        when(userServiceMock.findByName(TEST_USER.getUsername())).thenReturn(TEST_USER);
        when(addressRepositoryMock.save(any(Address.class))).thenReturn(TEST_ADDRESS);

        addressService.update(TEST_ADDRESS_DTO, TEST_USER.getId());

        verify(addressRepositoryMock).save(TEST_ADDRESS);
        verify(userServiceMock).findByName(TEST_USER.getUsername());
    }

    @Test
    void updateWhenValidationFailed() {
        TEST_USER.setAddress(null);
        when(userServiceMock.findByName(TEST_USER.getUsername())).thenReturn(TEST_USER);
        when(addressRepositoryMock.save(any(Address.class))).thenReturn(TEST_ADDRESS);

        final long testUserId = TEST_USER.getId();
        assertThrows(ValidationException.class, () -> addressService.update(TEST_ADDRESS_DTO, testUserId));

        verifyNoInteractions(addressRepositoryMock);
        verify(userServiceMock).findByName(TEST_USER.getUsername());
    }

    @Test
    void findById() {
        TEST_USER.setAddress(TEST_ADDRESS);
        when(userServiceMock.findByName(TEST_USER.getUsername())).thenReturn(TEST_USER);
        when(addressRepositoryMock.findById(TEST_ADDRESS.getId())).thenReturn(Optional.of(TEST_ADDRESS));

        final AddressDto addressDto = addressService.findById(TEST_ADDRESS.getId());

        assertEquals(TEST_ADDRESS.getId(), addressDto.getId());

        verify(userServiceMock).findByName(TEST_USER.getUsername());
        verify(addressRepositoryMock).findById(TEST_ADDRESS.getId());
    }

    @Test
    void findByIdNotFound() {
        TEST_USER.setAddress(TEST_ADDRESS);
        final long addressId = TEST_ADDRESS.getId();
        when(userServiceMock.findByName(TEST_USER.getUsername())).thenReturn(TEST_USER);
        when(addressRepositoryMock.findById(addressId)).thenReturn(Optional.empty());

        assertThrows(NoEntityFound.class, () -> addressService.findById(addressId));


        verifyNoInteractions(userServiceMock);
        verify(addressRepositoryMock).findById(addressId);
    }

    @Test
    void findAll() {
        when(addressRepositoryMock.findAll()).thenReturn(List.of(TEST_ADDRESS));

        final List<AddressDto> addressDtos = addressService.findAll();

        assertEquals(1, addressDtos.size());
    }

    @Test
    void deleteById() {
        TEST_USER.setAddress(TEST_ADDRESS);
        final long addressId = TEST_ADDRESS.getId();
        final String username = TEST_USER.getUsername();
        when(addressRepositoryMock.getById(addressId)).thenReturn(TEST_ADDRESS);
        when(userServiceMock.findByName(username)).thenReturn(TEST_USER);

        addressService.deleteById(addressId);

        verify(addressRepositoryMock).getById(addressId);
        verify(addressRepositoryMock).deleteById(addressId);
        verify(userServiceMock).findByName(username);
    }

    @Test
    void deleteByIdValidationFailed() {
        TEST_USER.setAddress(null);
        final long addressId = TEST_ADDRESS.getId();
        final String username = TEST_USER.getUsername();
        when(addressRepositoryMock.findById(addressId)).thenReturn(Optional.of(TEST_ADDRESS));
        when(userServiceMock.findByName(username)).thenReturn(TEST_USER);

        assertThrows(ValidationException.class, () -> addressService.deleteById(addressId));

        verify(addressRepositoryMock).getById(addressId);
        verifyNoMoreInteractions(addressRepositoryMock);
        verify(userServiceMock).findByName(username);
    }
}