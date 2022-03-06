package com.art.demo.web.controller;

import com.art.demo.model.dto.EntityListContainer;
import com.art.demo.model.dto.EntityMapContainer;
import com.art.demo.model.dto.OrderDto;
import com.art.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;

@RestController
@RequestMapping(OrderController.ORDERS_BASE)
public class OrderController implements ControllerCrud<OrderDto> {
    public static final String ID_PRODUCTS_PROD_ID_AMOUNT_AMOUNT_ENDPOINT = "/{id}/products/{prod_id}/amount/{amount}";
    protected static final String ORDERS_BASE = "/orders";
    private final OrderService orderService;

    public OrderController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public RedirectView create(final OrderDto orderDto) {
        orderService.create(orderDto);
        return new RedirectView("");
    }

    @Override
    public RedirectView update(final OrderDto orderDto, final long id) {
        orderService.update(orderDto, id);
        return new RedirectView("" + id);
    }

    @Override
    public ResponseEntity<OrderDto> findById(final long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @Override
    public ResponseEntity<EntityListContainer<OrderDto>> findAll() {
        final EntityListContainer<OrderDto> entityListContainer =
                new EntityListContainer<>("Order list", orderService.findAll());
        return ResponseEntity.ok(entityListContainer);
    }

    @Override
    public RedirectView deleteById(final long id) {
        orderService.deleteById(id);
        return new RedirectView("");
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<EntityMapContainer<String, String>> getOrderProducts(@PathVariable final long id) {
        final EntityMapContainer<String, String> orderProducts =
                new EntityMapContainer<>("Products in order : " + id, orderService.findById(id).getProductMap());
        return ResponseEntity.ok(orderProducts);
    }

    @PutMapping(ID_PRODUCTS_PROD_ID_AMOUNT_AMOUNT_ENDPOINT)
    public ResponseEntity<Void> addProducts(@PathVariable long id,
                                            @PathVariable("prod_id") long productId,
                                            @PathVariable BigDecimal amount) {
        orderService.addProduct(id, productId, amount);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/undo")
    public ResponseEntity<OrderDto> undo(@PathVariable long id) {
        return ResponseEntity.ok(orderService.undo(id));
    }
}
