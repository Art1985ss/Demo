package com.art.demo.web.controller;

import com.art.demo.model.dto.EntityListContainer;
import com.art.demo.model.dto.FoodDto;
import com.art.demo.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/products/food")
public class FoodController implements ControllerCrud<FoodDto> {
    final FoodService foodService;

    @Autowired
    public FoodController(final FoodService foodService) {
        this.foodService = foodService;
    }

    @Override
    public RedirectView create(final FoodDto foodDto) {
        foodService.create(foodDto);
        return new RedirectView("");
    }

    @Override
    public RedirectView update(final FoodDto foodDto, final long id) {
        foodService.update(foodDto, id);
        return new RedirectView("" + id);
    }

    @Override
    public ResponseEntity<FoodDto> findById(final long id) {
        return ResponseEntity.ok(foodService.findById(id));
    }

    @Override
    public ResponseEntity<EntityListContainer<FoodDto>> findAll() {
        final EntityListContainer<FoodDto> entityListContainer = new EntityListContainer<>("Food product list",
                                                                                           foodService.findAll());
        return ResponseEntity.ok(entityListContainer);
    }

    @Override
    public RedirectView deleteById(final long id) {
        foodService.deleteById(id);
        return new RedirectView("");
    }
}
