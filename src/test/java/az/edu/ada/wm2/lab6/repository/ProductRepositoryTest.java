package az.edu.ada.wm2.lab6.repository;

import az.edu.ada.wm2.lab6.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByExpirationDateBefore_shouldReturnResults() {
        Product product = Product.builder()
                .productName("Milk")
                .price(BigDecimal.TEN)
                .expirationDate(LocalDate.now().plusDays(2))
                .build();

        productRepository.save(product);

        List<Product> result =
                productRepository.findByExpirationDateBefore(LocalDate.now().plusDays(5));

        assertFalse(result.isEmpty());
    }

    @Test
    void findByPriceBetween_shouldReturnResults() {
        Product product = Product.builder()
                .productName("Milk")
                .price(BigDecimal.TEN)
                .expirationDate(LocalDate.now())
                .build();

        productRepository.save(product);

        List<Product> result =
                productRepository.findByPriceBetween(BigDecimal.ZERO, BigDecimal.TEN);

        assertFalse(result.isEmpty());
    }

    @Test
    void shouldReturnEmpty_whenNoMatch() {
        List<Product> result =
                productRepository.findByPriceBetween(BigDecimal.valueOf(100), BigDecimal.valueOf(200));

        assertTrue(result.isEmpty());
    }
}