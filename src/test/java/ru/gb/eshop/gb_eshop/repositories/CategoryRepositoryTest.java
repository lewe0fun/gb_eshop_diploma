package ru.gb.eshop.gb_eshop.repositories;

import jakarta.ws.rs.core.Application;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.gb.eshop.gb_eshop.models.Category;
import ru.gb.eshop.gb_eshop.models.Product;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = Application.class)
@AutoConfigureMockMvc
class CategoryRepositoryTest {

    @Mock
    private CategoryRepository categoryRepository;
    Category category;
    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    public void  CategoryRepo_Save_ReturnSavedCategory(){
        categoryRepository.save(category);

        assertNotNull(category);
    }

    @Test
    public void  CategoryRepo_Save_ReturnSavedCategoryId(){
        category.setId(1);

        categoryRepository.save(category);

        assertNotNull(category);
        assertEquals(category.getId(),1);
    }

    @Test
    public void  CategoryRepo_Save_ReturnSavedCategoryName(){
        category.setName("Ванны");

        categoryRepository.save(category);

        assertNotNull(category);
        assertEquals(category.getName(),"Ванны");
    }

}