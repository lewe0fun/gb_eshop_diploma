package ru.gb.eshop.gb_eshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.models.Cart;

import java.util.List;

/**
 * Репозиторий корзин
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Transactional
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    /**
     * Метод поиска корзин пользователя по id пользователя
     *
     * @param id id пользователя
     * @return корзины
     */
    List<Cart> findByPersonId(int id);

    /**
     * Метод удаления продукта по id
     * @param id id продукта
     */
    @Modifying
    @Query(value = "delete from product_cart where product_id=?1", nativeQuery = true)
    void deleteCartByProductId(int id);
}
