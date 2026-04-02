package az.edu.ada.wm2.lab6.model.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ProductRequestDto {
    private String productName;
    private BigDecimal price;
    private LocalDate expirationDate;
    private List<UUID> categoryIds;

    public ProductRequestDto() {
    }

    public ProductRequestDto(String productName, BigDecimal price, LocalDate expirationDate, List<UUID> categoryIds) {
        this.productName = productName;
        this.price = price;
        this.expirationDate = expirationDate;
        this.categoryIds = categoryIds;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<UUID> getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(List<UUID> categoryIds) {
        this.categoryIds = categoryIds;
    }
}
