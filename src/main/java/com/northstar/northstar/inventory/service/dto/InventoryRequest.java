package com.northstar.northstar.inventory.service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryRequest {

    @NotNull(message = "Store ID is required")
    private String storeId;

    @NotNull(message = "Book ID is required")
    private String bookId;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;
}
