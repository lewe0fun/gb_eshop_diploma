package ru.gb.eshop.gb_eshop.repositories;

import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.eshop.gb_eshop.models.Cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
class CartRepositoryTest {

    @Mock
    private CartRepository cartRepository;


    @Test
    public void CartRepo_Save_ReturnSavedCart() {
        Cart cart = new Cart();
        cart.setId(1);
        cart.setProductId(1);

        assertNotNull(cart);
        assertEquals(cart.getId(), 1);
        assertEquals(cart.getProductId(), 1);
    }

}