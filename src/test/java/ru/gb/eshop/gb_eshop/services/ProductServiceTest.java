package ru.gb.eshop.gb_eshop.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
        product=new Product();
        product.setPrice(100);
        product.setTitle("prod");
        product.setId(1);
    }

    @Test
    void getAllProduct() {
        //установка
        Product product1 = new Product();
        given(productRepository.findAll()).willReturn(List.of(product, product1));

        //условие
        List<Product> products = productRepository.findAll();

        //проверка
        assertThat(products).isNotNull();
        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void getProductId() {
        //установка
        given(productRepository.findById(1)).willReturn(Optional.of(product));

        //условие
        Product product1 = productService.getProductId(product.getId());

        //проверка
        assertThat(product1).isNotNull();
    }

    @Test
    void saveProduct() {
        //установка
        given(productRepository.save(product)).willReturn(product);

        //условие
        Product product1 = productService.saveProduct(product);

        //проверка
        assertThat(product1).isNotNull();
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void updateProduct() {
        //установка
        int id = 1;
        given(productRepository.save(product)).willReturn(product);

        //условие
        Product product1 = productService.updateProduct(id, product);

        //проверка
        assertThat(product1.getTitle()).isEqualTo("prod");
        assertThat(product1.getPrice()).isEqualTo(100);
    }

    @Test
    void deleteProduct() {
        //установка
        int id = 1;
        willDoNothing().given(productRepository).deleteById(id);

        //условие
        productService.deleteProduct(product.getId());

        //проверка
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void getProductFindByTitle() {
        //установка
        given(productRepository.findByTitle("prod")).willReturn(Optional.of(product));

        //условие
        Product product1 = productService.getProductFindByTitle(product);

        //проверка
        assertThat(product1).isNotNull();
    }
}