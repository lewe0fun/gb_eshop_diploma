package ru.gb.eshop.gb_eshop.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.gb.eshop.gb_eshop.enums.Status;


/**
 * Класс сущности корзины со свойствами id, personId, productId
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "product_cart")
public class Cart {

    /**
     * Поле id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Поле personId
     */
    @Column(name = "person_id")
    private int personId;

    /**
     * Поле productId
     */
    @Column(name = "product_id")
    private int productId;

    /**
     * Конструктор - создание нового объекта
     *
     * @see Cart#Cart(int,int)
     * @param personId - id пользователя
     * @param productId - id товара
     */
    public Cart(int personId, int productId) {
        this.personId = personId;
        this.productId = productId;
    }

}
