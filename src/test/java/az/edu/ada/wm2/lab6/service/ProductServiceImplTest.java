package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.Category;
import az.edu.ada.wm2.lab6.model.Product;
import az.edu.ada.wm2.lab6.model.dto.ProductRequestDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import az.edu.ada.wm2.lab6.model.mapper.ProductMapper;
import az.edu.ada.wm2.lab6.repository.CategoryRepository;
import az.edu.ada.wm2.lab6.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private UUID productId;
    private Product product;
    private ProductRequestDto requestDto;
    private ProductResponseDto responseDto;

    @BeforeEach
    void setUp() {
        productId = UUID.randomUUID();

        product = Product.builder()
                .id(productId)
                .productName("Milk")
                .price(BigDecimal.TEN)
                .expirationDate(LocalDate.now().plusDays(5))
                .build();

        requestDto = ProductRequestDto.builder()
                .productName("Milk")
                .price(BigDecimal.TEN)
                .expirationDate(LocalDate.now().plusDays(5))
                .build();

        responseDto = ProductResponseDto.builder()
                .id(productId)
                .productName("Milk")
                .price(BigDecimal.TEN)
                .expirationDate(LocalDate.now().plusDays(5))
                .build();
    }

    @Test
    void createProduct_shouldSaveAndReturnDto() {
        when(productMapper.toEntity(requestDto)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        ProductResponseDto result = productService.createProduct(requestDto);

        assertNotNull(result);
        verify(productRepository).save(product);
    }

    @Test
    void getProductById_shouldReturnProduct() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        ProductResponseDto result = productService.getProductById(productId);

        assertEquals(productId, result.getId());
    }

    @Test
    void getProductById_shouldThrow_whenNotFound() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> productService.getProductById(productId));
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        List<ProductResponseDto> result = productService.getAllProducts();

        assertEquals(1, result.size());
    }

    @Test
    void updateProduct_shouldUpdateAndReturn() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        ProductResponseDto result = productService.updateProduct(productId, requestDto);

        assertEquals("Milk", result.getProductName());
    }

    @Test
    void deleteProduct_shouldDelete() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.deleteProduct(productId);

        verify(productRepository).delete(product);
    }

    @Test
    void getProductsExpiringBefore_shouldReturnFiltered() {
        when(productRepository.findByExpirationDateBefore(any()))
                .thenReturn(List.of(product));
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        List<ProductResponseDto> result =
                productService.getProductsExpiringBefore(LocalDate.now().plusDays(10));

        assertEquals(1, result.size());
    }

    @Test
    void getProductsByPriceRange_shouldReturnFiltered() {
        when(productRepository.findByPriceBetween(any(), any()))
                .thenReturn(List.of(product));
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        List<ProductResponseDto> result =
                productService.getProductsByPriceRange(BigDecimal.ZERO, BigDecimal.TEN);

        assertEquals(1, result.size());
    }

    //OPTIONAL
    @Test
    void createProduct_shouldAttachCategories_ifProvided() {
        UUID categoryId = UUID.randomUUID();

        requestDto.setCategoryIds(List.of(categoryId));

        Category category = new Category();
        category.setId(categoryId);

        when(productMapper.toEntity(requestDto)).thenReturn(product);
        when(categoryRepository.findAllById(List.of(categoryId)))
                .thenReturn(List.of(category));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toResponseDto(product)).thenReturn(responseDto);

        productService.createProduct(requestDto);

        assertEquals(1, product.getCategories().size());
    }

    //ADDITIONAL
    @Test
    void createProduct_shouldThrow_whenPriceIsZeroOrNegative() {
        ProductRequestDto dto = ProductRequestDto.builder()
                .productName("Invalid Product")
                .price(BigDecimal.ZERO)
                .expirationDate(LocalDate.now().plusDays(1))
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> productService.createProduct(dto));
    }

    @Test
    void updateProduct_shouldThrow_whenPriceIsNegative() {
        UUID id = UUID.randomUUID();

        Product existing = new Product();
        existing.setId(id);

        ProductRequestDto dto = ProductRequestDto.builder()
                .price(BigDecimal.valueOf(-5))
                .build();

        assertThrows(IllegalArgumentException.class,
                () -> productService.updateProduct(id, dto));
    }
}