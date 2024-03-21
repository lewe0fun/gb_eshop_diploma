package ru.gb.eshop.gb_eshop.repositories;

import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.eshop.gb_eshop.models.Order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.gb.eshop.gb_eshop.enums.Status.ОЖИДАНИЕ;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
class OrderRepositoryTest {

    @Mock
    private OrderRepository orderRepository;

    @Test
    public void orderRepo_Save_ReturnSavedOrder() {
        Order order = new Order();
        order.setId(1);
        order.setNumber("100");
        order.setCount(25);
        order.setStatus(ОЖИДАНИЕ);
        order.setPrice(999);

        orderRepository.save(order);

        assertNotNull(order);
        assertEquals(order.getId(), 1);
        assertEquals(order.getStatus(), ОЖИДАНИЕ);
    }
}