package az.edu.ada.wm2.lab6.mapper;

import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    void shouldMapCategoriesToNames() {
        Category category = new Category();
        category.setName("Food");

        Product product = Product.builder()
                .categories(List.of(category))
                .build();

        ProductResponseDto dto = productMapper.toResponseDto(product);

        assertEquals
                ("Food", dto.getCategoryNames().getFirst());
    }

    //OPTIONAL
    @Test
    void shouldHandleEmptyCategories() {
        Product product = new Product();

        ProductResponseDto dto = productMapper.toResponseDto(product);

        assertNotNull(dto.getCategoryNames());
    }
}