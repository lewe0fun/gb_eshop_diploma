package ru.gb.eshop.gb_eshop.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;
import ru.gb.eshop.gb_eshop.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис заказов
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class OrderService {

    /**
     * Поле orderRepository
     */
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Метод сохранения заказа
     *
     * @param order заказ
     */
    @Transactional
    public Order save(Order order) {
       return orderRepository.save(order);
    }

    /**
     * Метод возвращает все заказы
     *
     * @return заказы
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Метод возвращает заказ по id
     *
     * @param id id товара
     * @return заказ
     */
    public Order getOrderById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    /**
     * Метод удаления заказа
     *
     * @param id id товара
     */
    @Transactional
    public void deleteOrder(int id){
        orderRepository.deleteById(id);
    }

    /**
     * Метод поиска заказов у пользователя
     *
     * @param person пользователь
     * @return список заказов
     */
    public List<Order> findByPerson(Person person) {
        return orderRepository.findByPerson(person);
    }
}
