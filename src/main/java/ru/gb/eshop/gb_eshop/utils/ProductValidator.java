package ru.gb.eshop.gb_eshop.utils;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.gb.eshop.gb_eshop.models.Product;
import ru.gb.eshop.gb_eshop.services.ProductService;

/**
 * Клас вспомогательный, для проверки отсутствующих/повторяющихся сущностей Product
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Component
public class ProductValidator implements Validator {

    /**
     * Поле productService
     */
    private final ProductService productService;

    /**
     * Конструктор - создание нового объекта
     *
     * @see ProductValidator#ProductValidator(ProductService)
     * @param productService - сервис товара
     */
    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Метод отсеивает классы на обрабатываемые и не обрабатываемые
     *
     * @param clazz класс
     * @return boolean значение
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    /**
     * Метод валидности объекта
     *
     * @param target объект
     * @param errors детали об ошибках
     */
    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;
        if (productService.getProductFindByTitle(product) != null) {
            errors.rejectValue("title", "", "Данное наименование товара уже используется");
        }
    }
}
