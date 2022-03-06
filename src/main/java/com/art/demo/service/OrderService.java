package com.art.demo.service;

import com.art.demo.config.WebSecurityConfig;
import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Order;
import com.art.demo.model.OrderHistoryService;
import com.art.demo.model.Product;
import com.art.demo.model.User;
import com.art.demo.model.dto.OrderDto;
import com.art.demo.model.mapper.OrderMapper;
import com.art.demo.repository.OrdersRepository;
import com.art.demo.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final ProductService productService;
    private final OrderHistoryService orderHistoryService;
    private final ValidationService<Order> validationService;

    @Autowired
    public OrderService(final OrdersRepository ordersRepository,
                        final UserRepository userRepository,
                        final ProductService productService) {
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.productService = productService;
        this.orderHistoryService = OrderHistoryService.getInstance();
        final Validator<Order> validator = order -> {
            final User user = getUser();
            if (!order.getUser().equals(user))
                throw new ValidationException("Orders can be manipulated only by users who created it!");
        };
        validationService = new ValidationService<>();
        validationService.addRule(validator);

    }

    @Override
    public OrderDto create(final OrderDto orderDto) {
        final User user = getUser();
        final Order order = fromDto(orderDto);
        order.setUser(user);
        orderHistoryService.save(order);
        return toDto(ordersRepository.save(order));
    }

    @Override
    public void update(final OrderDto orderDto, final long id) {
        final Order order1 = getById(id);
        validationService.validate(order1);
        orderHistoryService.save(order1);
        final Order order = fromDto(orderDto).setId(id);
        ordersRepository.save(order);
    }

    @Override
    public OrderDto findById(final long id) {
        final Order order = getById(id);
        validationService.validate(order);
        return toDto(order);
    }

    @Override
    public List<OrderDto> findAll() {
        final User user = getUser();
        return ordersRepository.findAll().stream()
                .filter(order -> order.getUser().equals(user))
                .map(OrderMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(final long id) {
        final Order order = getById(id);
        validationService.validate(order);
        ordersRepository.deleteById(id);
        orderHistoryService.remove(order);
    }

    public void addProduct(final long id,
                           final long productId,
                           final BigDecimal amount) {
        final Product product = productService.getById(productId);
        final Order order = ordersRepository.getById(id);
        validationService.validate(order);
        orderHistoryService.save(order);
        final Map<Product, BigDecimal> productsMap = order.getProductsMap();
        if (productsMap.containsKey(product)) {
            productsMap.put(product, productsMap.get(product).add(amount));
        } else {
            productsMap.put(product, amount);
        }
        ordersRepository.save(order);
    }

    public OrderDto undo(final long id) {
        final Order order = getById(id);
        validationService.validate(order);
        orderHistoryService.undo(order);
        ordersRepository.save(order);
        return OrderMapper.toDto(order);
    }

    private Order getById(final long id) {
        final Order order = ordersRepository.findById(id)
                .orElseThrow(() -> new NoEntityFound("id", Order.class));
        validationService.validate(order);
        return order;
    }

    private User getUser() {
        final String username = WebSecurityConfig.getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoEntityFound("username " + username, User.class));
    }
}
