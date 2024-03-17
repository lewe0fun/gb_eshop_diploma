package ru.gb.eshop.gb_eshop.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.eshop.gb_eshop.models.Image;
import ru.gb.eshop.gb_eshop.models.Product;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Класс загрузчика картинок
 */
@Component
public class ImageUploader {

    /**
     * Поле uploadPath
     */
    @Value("${upload.path}")
    private String uploadPath;
    /**
     * Добавление картинки товару
     *
     * @param file         картинка
     * @param product      товар
     * @throws IOException если возникнут ошибки
     */
    public void setImage(MultipartFile file, Product product) throws IOException {
        if (!file.isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            Image image = new Image();
            image.setProduct(product);
            image.setFileName(fileName);
            product.addImageProduct(image);
        }
    }

}
