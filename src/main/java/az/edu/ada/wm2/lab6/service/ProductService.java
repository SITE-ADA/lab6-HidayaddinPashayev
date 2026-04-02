package az.edu.ada.wm2.lab6.service;

import az.edu.ada.wm2.lab6.model.dto.ProductRequestDto;
import az.edu.ada.wm2.lab6.model.dto.ProductResponseDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto product);
    ProductResponseDto getProductById(UUID id);
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto updateProduct(UUID id, ProductRequestDto product);
    void deleteProduct(UUID id);
    List<ProductResponseDto> getProductsExpiringBefore(LocalDate date);
    List<ProductResponseDto> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);
}
