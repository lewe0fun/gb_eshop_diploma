package ru.gb.eshop.gb_eshop.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс сущности товар со свойствами id, title, description, price,
 * warehouse, seller, category, dateTime, imageList, personList, orderList
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Data
@NoArgsConstructor
@Entity
public class Product {

    /**
     * Поле id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Поле title
     */
    @Column(name = "title", nullable = false, columnDefinition = "text", unique = true)
    @NotEmpty(message = "Наименование товара не может быть пустым")
    private String title;

    /**
     * Поле description
     */
    @Column(name = "description", nullable = false, columnDefinition = "text")
    @NotEmpty(message = "Описание товара не может быть пустым")
    private String description;

    /**
     * Поле price
     */
    @Column(name = "price", nullable = false)
    @Min(value = 1, message = "Цена товара не может быть отрицательной или нулевой")
    private float price;

    /**
     * Поле warehouse
     */
    @Column(name = "warehouse", nullable = false)
    @NotEmpty(message = "Склад по нахождению товара не может быть пустым")
    private String warehouse;

    /**
     * Поле seller
     */
    @Column(name = "seller", nullable = false)
    @NotEmpty(message = "Информация о продавце не может быть пустой")
    private String seller;

    /**
     * Поле category
     */
    @ManyToOne(optional = false)
    private Category category;

    /**
     * Поле dateTime
     */
    private LocalDateTime dateTime;

    /**
     * Поле imageList
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    private List<Image> imageList = new ArrayList<>();

    /**
     * Поле personList
     */
    @ManyToMany()
    @JoinTable(name = "product_cart", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "person_id"))
    private List<Person> personList;

    /**
     * Поле orderList
     */
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<Order> orderList;

    /**
     * Конструктор - создание нового объекта
     *
     * @see Product#Product(String,String,float,String,String,Category,LocalDateTime,List,boolean)
     * @param title - название
     * @param description - описание
     * @param price - цена
     * @param warehouse - склад
     * @param seller - продавец
     * @param category - категория
     * @param dateTime - дата создания
     * @param imageList - постеры товара
     * @param demo - представление
     */
    public Product(String title, String description, float price, String warehouse, String seller, Category category,
                   LocalDateTime dateTime, List<Image> imageList, boolean demo) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.warehouse = warehouse;
        this.seller = seller;
        this.category = category;
        this.dateTime = dateTime;
        this.imageList = imageList;
    }

    /**
     * Метод автоматического указания времени создания
     */
    @PrePersist
    private void init() {
        dateTime = LocalDateTime.now();
    }

    /**
     * Метод добавления товару картинки
     *
     * @param image
     */
    public void addImageProduct(Image image) {
        image.setProduct(this);
        imageList.add(image);
    }
}
