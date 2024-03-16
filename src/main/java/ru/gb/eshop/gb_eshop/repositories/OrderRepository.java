package ru.gb.eshop.gb_eshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.eshop.gb_eshop.models.Order;
import ru.gb.eshop.gb_eshop.models.Person;

import java.util.List;

/**
 * Репозиторий заказов
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    /**
     * Получение списка заказов по конкретному пользователю
     * @param person пользователь
     * @return список заказов
     */
    List<Order> findByPerson(Person person);

    /**
     * Получение списка заказов по ключевому слову
     * @param endingWith ключевое слово
     * @return список заказов
     */
    List<Order> findByNumberEndingWith(String endingWith);

    /**
     * Получение заказа по продукту
     * @param product_id id продукта
     * @return заказ
     */
    Order findByProduct_id(int product_id);
}
