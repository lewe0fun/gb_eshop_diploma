package ru.gb.eshop.gb_eshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.eshop.gb_eshop.models.Product;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий продуктов
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Поиск всех продуктов по части наименования продукта в не зависимости от регистра
     *
     * @param title ключевое слово
     * @return список товаров
     */
    List<Product> findByTitleContainingIgnoreCase(String title);

    /**
     * Поиск по наименованию и фильтрация по диапазону цены
     *
     * @param title ключевое слово
     * @param ot    цена от
     * @param Do    цена до
     * @return список товаров
     */
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndPriceGreaterThanEqualAndPriceLessThanEqual(String title, float ot, float Do);

    /**
     * Поиск по наименованию и фильтрация по диапазону цены, а также сортировка по возрастанию цены
     *
     * @param title ключевое слово
     * @param ot    цена от
     * @param Do    цена до
     * @return список товаров
     */
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title, float ot, float Do);

    /**
     * Поиск по наименованию и фильтрация по диапазону цены, а также сортировка по убыванию цены
     *
     * @param title ключевое слово
     * @param ot    цена от
     * @param Do    цена до
     * @return список товаров
     */
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title, float ot, float Do);

    /**
     * Поиск по наименованию и фильтрация по диапазону цены, сортировка по возрастанию цены, а также фильтрация по категории
     *
     * @param title    ключевое слово
     * @param ot       цена от
     * @param Do       цена до
     * @param category категория
     * @return список товаров
     */
    @Query(value = "select * from product where category_id = ?4 and((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, float ot, float Do, int category);

    /**
     * Поиск по наименованию и фильтрация по диапазону цены, сортировка по убыванию цены, а также фильтрация по категории
     *
     * @param title    ключевое слово
     * @param ot       цена от
     * @param Do       цена до
     * @param category категория
     * @return список товаров
     */
    @Query(value = "select * from product where category_id = ?4 and((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3) order by price desc", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, float ot, float Do, int category);

    /**
     * Поиск товара по названию
     *
     * @param title название
     * @return товар
     */
    Optional<Product> findByTitle(String title);

    /**
     * Поиск товаров по названию и категории
     *
     * @param title ключевое слово
     * @param id    id категории
     * @return список товаров
     */
    @Query(value = "select * from product where category_id = ?2 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1'))", nativeQuery = true)
    List<Product> findByTitleAndCategory(String title, int id);

    /**
     * Поиск товаров по названию в порядке возрастания цены
     *
     * @param title ключевое слово
     * @return список товаров
     */
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) order by price", nativeQuery = true)
    List<Product> findByTitleOrderByPriceAsc(String title);

    /**
     * Поиск товаров по названию в порядке убывания цены
     *
     * @param title ключевое слово
     * @return список товаров
     */
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) order by price desc", nativeQuery = true)
    List<Product> findByTitleOrderByPriceDesc(String title);

    /**
     * Поиск товаров по названию и категории по возрастанию
     *
     * @param title ключевое слово
     * @param id_category категории
     * @return список товаров
     */
    @Query(value = "select * from product where category_id = ?2 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) order by price", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceAsc(String title, int id_category);

    /**
     * Поиск товаров по названию и категории по убыванию
     *
     * @param title ключевое слово
     * @param id_category категории
     * @return список товаров
     */
    @Query(value = "select * from product where category_id = ?2 and ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) order by price desc", nativeQuery = true)
    List<Product> findByTitleAndCategoryOrderByPriceDesc(String title, int id_category);

    /**
     * Поиск товаров по названию и по цене от и до
     *
     * @param title ключевое слово
     * @param ot цена от
     * @param Do цена до
     * @return список товаров
     */
    @Query(value = "select * from product where ((lower(title) LIKE %?1%) or (lower(title) LIKE '?1%') OR (lower(title) LIKE '%?1')) and (price >= ?2 and price <= ?3)", nativeQuery = true)
    List<Product> findByTitleAndCategoryAndPrice(String title, float ot, float Do);
}