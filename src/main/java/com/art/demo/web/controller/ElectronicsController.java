package com.art.demo.web.controller;

import com.art.demo.model.dto.ElectronicsDto;
import com.art.demo.model.dto.EntityListContainer;
import com.art.demo.service.ElectronicsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("products/electronics")
public class ElectronicsController implements ControllerCrud<ElectronicsDto> {
    private final ElectronicsService electronicsService;

    public ElectronicsController(final ElectronicsService electronicsService) {
        this.electronicsService = electronicsService;
    }

    @Override
    public RedirectView create(final ElectronicsDto electronicsDto) {
        electronicsService.create(electronicsDto);
        return new RedirectView("");
    }

    @Override
    public RedirectView update(final ElectronicsDto electronicsDto, final long id) {
        return new RedirectView("" + id);
    }

    @Override
    public ResponseEntity<ElectronicsDto> findById(final long id) {
        return ResponseEntity.ok(electronicsService.findById(id));
    }

    @Override
    public ResponseEntity<EntityListContainer<ElectronicsDto>> findAll() {
        final EntityListContainer<ElectronicsDto> entityListContainer =
                new EntityListContainer<>("Electronics list", electronicsService.findAll());
        return ResponseEntity.ok(entityListContainer);
    }

    @Override
    public RedirectView deleteById(final long id) {
        electronicsService.deleteById(id);
        return new RedirectView("");
    }
}
