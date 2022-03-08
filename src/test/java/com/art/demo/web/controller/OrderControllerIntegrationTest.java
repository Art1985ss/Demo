package com.art.demo.web.controller;

import com.art.demo.model.Order;
import com.art.demo.model.Product;
import com.art.demo.model.User;
import com.art.demo.model.mapper.AddressMapperTest;
import com.art.demo.model.mapper.OrderMapperTest;
import com.art.demo.model.mapper.ProductMapperTest;
import com.art.demo.model.mapper.UserMapperTest;
import com.art.demo.repository.OrdersRepository;
import com.art.demo.repository.ProductRepository;
import com.art.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;

import static com.art.demo.web.controller.OrderController.ID_PRODUCTS_PROD_ID_AMOUNT_AMOUNT_ENDPOINT;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {
    @MockBean
    private OrdersRepository ordersRepositoryMock;
    @MockBean
    private ProductRepository productRepositoryMock;
    @MockBean
    private UserRepository userRepositoryMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void addProductTest() throws Exception {
        final Product food = ProductMapperTest.TEST_PRODUCT;
        final User user = UserMapperTest.createUser().setAddress(AddressMapperTest.createAddress());
        final Order order = OrderMapperTest.createOrder().setUser(user);
        final String username = user.getUsername();
        final long testId = 1L;
        when(ordersRepositoryMock.getById(testId)).thenReturn(order);
        when(productRepositoryMock.findById(testId)).thenReturn(Optional.ofNullable(food));
        when(userRepositoryMock.findByUsername(username)).thenReturn(Optional.of(user));

        final MockHttpServletRequestBuilder putRequest = put(OrderController.ORDERS_BASE + ID_PRODUCTS_PROD_ID_AMOUNT_AMOUNT_ENDPOINT, 1, 1, 10)
                .with(user(username).password("pass").roles("USER", "ADMIN"));

        mockMvc.perform(putRequest)
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        assertAll(
                () -> verify(ordersRepositoryMock).getById(testId),
                () -> verify(ordersRepositoryMock).save(order),
                () -> verify(productRepositoryMock).findById(testId),
                () -> verify(userRepositoryMock).findByUsername(username),
                () -> verifyNoMoreInteractions(productRepositoryMock),
                () -> verifyNoMoreInteractions(userRepositoryMock),
                () -> verifyNoMoreInteractions(ordersRepositoryMock),

                () -> assertEquals(1, order.getProductsMap().size()),
                () -> assertTrue(order.getProductsMap().containsKey(food)),
                () -> assertEquals(BigDecimal.valueOf(10), order.getProductsMap().get(food))
        );
    }
}