package ru.gb.eshop.gb_eshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;

/**
 * Сервис товаров
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Service
@Transactional(readOnly = true)
public class ProductService {

    /**
     * Поле productRepository
     */
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Метод возвращает все товары
     *
     * @return список товаров
     */
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    /**
     * Метод поиска товара по id
     *
     * @param id id товара
     * @return найденный товар
     */
    public Product getProductId(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return optionalProduct.orElse(null);
    }

    /**
     * Метод сохранения товара
     *
     * @param product товар
     */
    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Обновление товара
     *
     * @param id      id товара, который будет изменен
     * @param product товар с обновленными данными
     */
    @Transactional
    public Product updateProduct(int id, Product product) {
        product.setId(id);
        return productRepository.save(product);
    }

    /**
     * Удаление продукта по id
     *
     * @param id id продукта
     */
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    /**
     * Метод для валидации товара
     */
    public Product getProductFindByTitle(Product product) {
        Optional<Product> product_db = productRepository.findByTitle(product.getTitle());
        return product_db.orElse(null);
    }
}
