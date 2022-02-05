package com.art.demo.web.controller;

import com.art.demo.model.dto.EntityListContainer;
import com.art.demo.model.dto.UserDto;
import com.art.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/users")
public class UserController implements ControllerCrud<UserDto> {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @Override
    public RedirectView create(final UserDto userDto) {
        userService.create(userDto);
        return new RedirectView("");
    }

    @Override
    public RedirectView update(final UserDto userDto, final long id) {
        userService.update(userDto, id);
        return new RedirectView("");
    }

    @Override
    public ResponseEntity<UserDto> findById(final long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Override
    public ResponseEntity<EntityListContainer<UserDto>> findAll() {
        return ResponseEntity.ok(new EntityListContainer<>("User list", userService.findAll()));
    }

    @Override
    public RedirectView deleteById(final long id) {
        userService.deleteById(id);
        return new RedirectView("");
    }
}
