package ru.gb.eshop.gb_eshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    // Получение списка заказов по конкретному пользователю Person
    List<Order> findByPerson(Person person);

    List<Order> findByNumberEndingWith(String endingWith);
}
