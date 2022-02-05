package com.art.demo.service;

import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.model.Order;
import com.art.demo.model.Product;
import com.art.demo.model.dto.OrderDto;
import com.art.demo.model.mapper.OrderMapper;
import com.art.demo.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.art.demo.model.mapper.OrderMapper.fromDto;
import static com.art.demo.model.mapper.OrderMapper.toDto;

@Service
public class OrderService implements CRUD<OrderDto> {
    private final OrdersRepository ordersRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(final OrdersRepository ordersRepository, final ProductService productService) {
        this.ordersRepository = ordersRepository;
        this.productService = productService;
    }

    @Override
    public OrderDto create(final OrderDto orderDto) {
        return toDto(ordersRepository.save(fromDto(orderDto)));
    }

    @Override
    public void update(final OrderDto orderDto, final long id) {
        ordersRepository.save(fromDto(orderDto).setId(id));
    }

    @Override
    public OrderDto findById(final long id) {
        return toDto(getId(id));
    }

    @Override
    public List<OrderDto> findAll() {
        return ordersRepository.findAll().stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public void delete(final OrderDto orderDto) {
        ordersRepository.delete(fromDto(orderDto));
    }

    @Override
    public void deleteById(final long id) {
        ordersRepository.deleteById(id);
    }


    public void addProduct(final long id,
                           final long productId,
                           final BigDecimal amount) {
        final Product product = productService.getById(productId);
        final Order order = ordersRepository.getById(id);
        final Map<Product, BigDecimal> productsMap = order.getProductsMap();
        if (productsMap.containsKey(product)) {
            productsMap.put(product, productsMap.get(product).add(amount));
        } else {
            productsMap.put(product, amount);
        }
        ordersRepository.save(order);
    }

    private Order getId(final long id) {
        return ordersRepository.findById(id)
                .orElseThrow(() -> new NoEntityFound("id", Order.class));
    }
}
