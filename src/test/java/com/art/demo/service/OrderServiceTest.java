package com.art.demo.service;

import com.art.demo.config.WebSecurityConfig;
import com.art.demo.exceptions.NoEntityFound;
import com.art.demo.exceptions.ValidationException;
import com.art.demo.model.Order;
import com.art.demo.model.Product;
import com.art.demo.model.User;
import com.art.demo.model.dto.OrderDto;
import com.art.demo.model.mapper.AddressMapperTest;
import com.art.demo.model.mapper.OrderMapperTest;
import com.art.demo.model.mapper.ProductMapperTest;
import com.art.demo.model.mapper.UserMapperTest;
import com.art.demo.repository.OrdersRepository;
import com.art.demo.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {
    private final static User TEST_USER = UserMapperTest.createUser().setAddress(AddressMapperTest.createAddress());
    private final static Order TEST_ORDER = OrderMapperTest.createOrder();
    private static final long TEST_ID = TEST_ORDER.getId();
    private final static OrderDto TEST_ORDER_DTO = OrderMapperTest.createDtoOrder();
    private final OrdersRepository ordersRepository = mock(OrdersRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final ProductService productService = mock(ProductService.class);
    private final OrderService orderService;
    private MockedStatic<WebSecurityConfig> webSecurityConfigMockedStatic;

    public OrderServiceTest() {
        orderService = new OrderService(ordersRepository, userRepository, productService);
    }

    @BeforeEach
    void setUp() {
        TEST_ORDER.setUser(TEST_USER);
        final Authentication authentication = mock(Authentication.class);
        webSecurityConfigMockedStatic = mockStatic(WebSecurityConfig.class);
        webSecurityConfigMockedStatic.when(WebSecurityConfig::getAuthentication).thenReturn(authentication);
        when(userRepository.findByUsername(TEST_USER.getUsername())).thenReturn(Optional.of(TEST_USER));
        when(authentication.getName()).thenReturn(TEST_USER.getUsername());
    }

    @AfterEach
    void tearDown() {
        TEST_ORDER.setUser(null);
        reset(ordersRepository, userRepository, productService);
        webSecurityConfigMockedStatic.close();
    }

    @Test
    void create() {
        when(ordersRepository.save(TEST_ORDER)).thenReturn(TEST_ORDER);

        final OrderDto actualResponse = orderService.create(TEST_ORDER_DTO);

        verify(ordersRepository).save(TEST_ORDER);
        assertEquals(TEST_ORDER_DTO.getId(), actualResponse.getId());
    }

    @Test
    void update() {
        when(ordersRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_ORDER));

        orderService.update(TEST_ORDER_DTO, TEST_ID);

        verify(ordersRepository).save(TEST_ORDER);
    }

    @Test
    void findById() {
        when(ordersRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_ORDER));

        final OrderDto actualResponse = orderService.findById(TEST_ID);

        verify(ordersRepository).findById(TEST_ID);
        assertEquals(TEST_ID, actualResponse.getId());
    }

    @Test
    void findByIdValidationFailed() {
        TEST_ORDER.setUser(new User());
        when(ordersRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_ORDER));

        assertThrows(ValidationException.class, () -> orderService.findById(TEST_ID));

        verify(ordersRepository).findById(TEST_ID);
    }

    @Test
    void findByIdNotFound() {
        TEST_ORDER.setUser(new User());
        when(ordersRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThrows(NoEntityFound.class, () -> orderService.findById(TEST_ID));

        verify(ordersRepository).findById(TEST_ID);
    }

    @Test
    void findAll() {
        when(ordersRepository.findAll()).thenReturn(List.of(TEST_ORDER));

        final List<OrderDto> actualResponse = orderService.findAll();

        verify(ordersRepository).findAll();
        verify(userRepository).findByUsername(TEST_USER.getUsername());
        assertEquals(1, actualResponse.size());
        assertEquals(TEST_ORDER_DTO.getId(), actualResponse.get(0).getId());
    }

    @Test
    void deleteById() {
        when(ordersRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(TEST_ORDER));

        orderService.deleteById(TEST_ID);

        verify(ordersRepository).deleteById(TEST_ID);
        verify(userRepository, times(2)).findByUsername(TEST_USER.getUsername());
    }

    @Test
    void addProduct() {
        final Product testProduct = ProductMapperTest.TEST_PRODUCT;
        when(productService.getById(TEST_ID)).thenReturn(testProduct);
        when(ordersRepository.getById(TEST_ID)).thenReturn(TEST_ORDER);

        orderService.addProduct(TEST_ID, TEST_ID, BigDecimal.TEN);

        verify(productService).getById(TEST_ID);
        verify(ordersRepository).getById(TEST_ID);
        verify(ordersRepository).save(TEST_ORDER);
        assertEquals(1, TEST_ORDER.getProductsMap().size(), 1);
        assertEquals(BigDecimal.TEN, TEST_ORDER.getProductsMap().get(testProduct));

        orderService.addProduct(TEST_ID, TEST_ID, BigDecimal.TEN);

        assertEquals(1, TEST_ORDER.getProductsMap().size(), 1);
        assertEquals(BigDecimal.TEN.multiply(BigDecimal.valueOf(2)), TEST_ORDER.getProductsMap().get(testProduct));

        when(ordersRepository.findById(TEST_ID)).thenReturn(Optional.of(TEST_ORDER));

        orderService.undo(TEST_ID);

        assertEquals(1, TEST_ORDER.getProductsMap().size(), 1);
        assertEquals(BigDecimal.TEN, TEST_ORDER.getProductsMap().get(testProduct));
    }
}