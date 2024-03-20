package ru.gb.eshop.gb_eshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.gb.eshop.gb_eshop.models.Cart;
import ru.gb.eshop.gb_eshop.repositories.CartRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class CartServiceTest {
    @Mock
    private CartRepository cartRepository;

    @InjectMocks
    private CartService cartService;
    private Cart cart;

    @BeforeEach
    void setUp() {
        cartRepository = Mockito.mock(CartRepository.class);
        cartService = new CartService(cartRepository);
        cart = new Cart(2, 3);
        cart.setId(1);
    }
    @DisplayName("JUnit test for findById method")
    @Test
    void findByPersonId() {
        //установка
        int person_id = 2;
        Cart cart1 = new Cart(person_id, 6);
        cart1.setId(4);
        given(cartRepository.findByPersonId(person_id)).willReturn(List.of(cart, cart1));

        //условие
        List<Cart> carts = cartService.findByPersonId(person_id);

        //проверка
        assertThat(carts).isNotNull();
        assertThat(carts.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for delete method")
    @Test
    void deleteCartByProductId() {
        //установка
        int product_id = 3;
        willDoNothing().given(cartRepository).deleteCartByProductId(product_id);

        //условие
        cartService.deleteCartByProductId(cart.getProductId());

        //проверка
        verify(cartRepository, times(1)).deleteCartByProductId(product_id);
    }

    @DisplayName("JUnit test for save method")
    @Test
    void save() {
        //установка
        given(cartRepository.save(cart)).willReturn(cart);

        //условие
        Cart savedCart = cartService.save(cart);

        //проверка
        assertThat(savedCart).isNotNull();
        verify(cartRepository, times(1)).save(cart);
    }
}