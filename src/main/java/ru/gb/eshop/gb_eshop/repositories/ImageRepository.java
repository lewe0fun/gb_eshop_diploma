package ru.gb.eshop.gb_eshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.eshop.gb_eshop.models.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
}