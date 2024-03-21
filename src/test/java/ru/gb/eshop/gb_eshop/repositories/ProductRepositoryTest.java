package ru.gb.eshop.gb_eshop.repositories;

import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.eshop.gb_eshop.models.Cart;
import ru.gb.eshop.gb_eshop.models.Category;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.services.CartService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
class ProductRepositoryTest {

    @Mock
    private ProductRepository productRepository;
    Product product;
    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setTitle("product");
        product.setPrice(123);
        product.setDescription("productDesc");
        product.setWarehouse("MoscvaSclad");
        product.setSeller("Prodavec");
    }
    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void productRepo_SaveProduct_ReturnProductId() {
        productRepository.save(product);

        assertNotNull(product);
    }

    @Test
    public void productRepo_Save_ReturnSavedProductId() {
        productRepository.save(product);

        assertNotNull(product);
        assertEquals(product.getId(), 1);
    }

    @Test
    public void productRepo_Save_ReturnSavedProductCategory() {
        Category category = new Category();

        categoryRepository.save(category);

        product.setCategory(category);
        productRepository.save(product);

        assertNotNull(product);
        assertNotNull(category);
        assertEquals(product.getCategory(), category);
    }


}