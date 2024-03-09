package ru.gb.eshop.gb_eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.models.Cart;
import ru.gb.eshop.gb_eshop.repositories.CartRepository;

import java.util.List;

/**
 * Сервис корзин
 */
@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Поиск корзин по id пользователя
     *
     * @param id id пользователя
     * @return список корзин
     */
    public List<Cart> findByPersonId(int id) {
        return cartRepository.findByPersonId(id);
    }

    /**
     * Удаление корзины по id товара
     *
     * @param id товара
     */
    @Transactional
    public void deleteCartByProductId(int id) {
        cartRepository.deleteCartByProductId(id);
    }

    /**
     * Создание корзины
     *
     * @param cart корзина
     */
    @Transactional
    public void save(Cart cart) {
        cartRepository.save(cart);
    }
}
