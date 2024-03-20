package ru.gb.eshop.gb_eshop.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Основной класс конфигурации
 *
 * @author Пакулин Ю.А., Строев Д.В., Брылин М.В.
 * @version 1.0
 */
@Configuration
@ComponentScan("ru.gb.eshop.gb_eshop.utils")
public class MainConfig implements WebMvcConfigurer {

    /**
     * Путь загрузки изображений
     */
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Добавление пути сохранения изображений в ресурсы
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pics/**").addResourceLocations("file:///" + uploadPath + "/", "/images/");
    }
}
