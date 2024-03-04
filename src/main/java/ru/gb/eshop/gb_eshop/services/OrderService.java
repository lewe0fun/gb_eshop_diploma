package ru.gb.eshop.gb_eshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;


    @Transactional
    public Order getAllOrders(int id, Order order) {
        order.setId(id);
        orderRepository.save(order);
        return order;
    }

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    public void updateOrder(int id, Order order) {
        order.setId(id);
        orderRepository.save(order);
    }

    @Transactional
    public void updateOrderStatus(Order order) {
        orderRepository.save(order);
    }

    public Order getOrderById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Transactional
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }
}
