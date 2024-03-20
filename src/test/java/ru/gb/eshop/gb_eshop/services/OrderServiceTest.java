package ru.gb.eshop.gb_eshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;
    private Order order;
    @BeforeEach
    void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        orderService = new OrderService(orderRepository);
        order = new Order();
        order.setId(1);
        order.setNumber("100");
        order.setCount(25);
        order.setCount(5);
        order.setPrice(999);
    }

    @Test
    void save() {
        //установка
        given(orderRepository.save(order)).willReturn(order);

        //условие
        Order savedCart = orderService.save(order);

        //проверка
        assertThat(savedCart).isNotNull();
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void getAllOrders() {
        //установка
        Order order1=new Order();
        given(orderRepository.findAll()).willReturn(List.of(order, order1));

        //условие
        List<Order> orders = orderRepository.findAll();

        //проверка
        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(2);
    }

    @Test
    void getOrderById() {
        //установка
        given(orderRepository.findById(1)).willReturn(Optional.of(order));

        //условие
        Order order1 = orderService.getOrderById(order.getId());

        //проверка
        assertThat(order1).isNotNull();
    }

    @Test
    void deleteOrder() {
        //установка
        int order_id = 1;
        willDoNothing().given(orderRepository).deleteById(order_id);

        //условие
        orderService.deleteOrder(order.getId());

        //проверка
        verify(orderRepository, times(1)).deleteById(order_id);
    }

    @Test
    void findByPerson() {
        //установка
        Person person = new Person();
        Order order1 = new Order();
        given(orderRepository.findByPerson(person)).willReturn(List.of(order, order1));

        //условие
        List<Order> orders = orderService.findByPerson(person);

        //проверка
        assertThat(orders).isNotNull();
        assertThat(orders.size()).isEqualTo(2);
    }
}