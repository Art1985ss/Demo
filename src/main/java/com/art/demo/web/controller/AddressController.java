package com.art.demo.web.controller;

import com.art.demo.model.dto.AddressDto;
import com.art.demo.model.dto.EntityListContainer;
import com.art.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/addresses")
public class AddressController implements ControllerCrud<AddressDto>{
    private final AddressService addressService;

    @Autowired
    public AddressController(final AddressService addressService) {
        this.addressService = addressService;
    }

    @Override
    public RedirectView create(final AddressDto addressDto) {
        addressService.create(addressDto);
        return new RedirectView("");
    }

    @Override
    public RedirectView update(final AddressDto addressDto, final long id) {
        return null;
    }

    @Override
    public ResponseEntity<AddressDto> findById(final long id) {
        return null;
    }

    @Override
    public ResponseEntity<EntityListContainer<AddressDto>> findAll() {
        return null;
    }

    @Override
    public RedirectView deleteById(final long id) {
        return null;
    }
}
