package ru.gb.eshop.gb_eshop.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.eshop.gb_eshop.models.Category;

/**
 * Репозиторий категорий
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Метод поиска категории по названию
     * @param name название категории
     * @return категория
     */
    Category findByName(String name);
}