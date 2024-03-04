package ru.gb.eshop.gb_eshop.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.eshop.gb_eshop.enums.Status;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Person person;

    private int count;
    private float price;
    private LocalDateTime dateTime;
    //@Enumerated(EnumType.STRING)
    private Status status;

    @PrePersist
    private void init(){
        dateTime = LocalDateTime.now();
    }

    public Order(String number, Product product, Person person, int count, float price, Status status) {
        this.number = number;
        this.product = product;
        this.person = person;
        this.count = count;
        this.price = price;
        this.status = status;
    }
}
