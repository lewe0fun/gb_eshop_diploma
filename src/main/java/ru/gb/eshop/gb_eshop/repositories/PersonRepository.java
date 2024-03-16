package ru.gb.eshop.gb_eshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.eshop.gb_eshop.models.Person;

import java.util.Optional;

/**
 * Репозиторий пользователей
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    /**
     * Метод поиска пользователя по логину
     * @param login логин
     * @return пользователь
     */
    Optional<Person> findByLogin(String login);

    /**
     * Метод обновления пользователя (в разработке)
     * @param id id пользователя
     * @param password пароль пользователя
     */
    @Modifying
    @Query(value = "UPDATE person SET password = ?2 WHERE id= ?1", nativeQuery = true)
    void changePasswordPersonById(int id, String password);
}
