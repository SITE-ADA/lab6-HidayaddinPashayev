package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.dto.CategoryRequestDto;
import az.edu.ada.wm2.lab6.model.dto.CategoryResponseDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import az.edu.ada.wm2.lab6.repository.ProductRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private UUID categoryId;
    private UUID productId;

    @Test
    void create_shouldSaveCategory() {
        CategoryRequestDto dto = new CategoryRequestDto("Food");

        Category category = new Category();
        category.setName("Food");

        when(categoryRepository.save(any())).thenReturn(category);

        CategoryResponseDto result = categoryService.create(dto);

        assertEquals("Food", result.getName());
    }

    @Test
    void getAll_shouldReturnList() {
        Category category = new Category();
        category.setName("Food");

        when(categoryRepository.findAll()).thenReturn(List.of(category));

        List<CategoryResponseDto> result = categoryService.getAll();

        assertEquals(1, result.size());
    }

    @Test
    void addProduct_shouldLinkProductToCategory() {
        categoryId = UUID.randomUUID();
        productId = UUID.randomUUID();

        Category category = new Category();
        Product product = new Product();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        categoryService.addProduct(categoryId, productId);

        assertTrue(product.getCategories().contains(category));
        verify(productRepository).save(product);
    }

    @Test
    void getProducts_shouldReturnDtos() {
        categoryId = UUID.randomUUID();

        Category category = new Category();

        Product product = new Product();
        category.setProducts(List.of(product));

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        when(productMapper.toResponseDto(product)).thenReturn(new ProductResponseDto());

        List<ProductResponseDto> result = categoryService.getProducts(categoryId);

        assertEquals(1, result.size());
    }

    //OPTIONAL
    @Test
    void addProduct_shouldThrow_whenCategoryNotFound() {
        when(categoryRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> categoryService.addProduct(UUID.randomUUID(), UUID.randomUUID()));
    }
}