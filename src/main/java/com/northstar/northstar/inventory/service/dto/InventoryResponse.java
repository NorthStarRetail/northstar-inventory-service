package com.northstar.northstar.inventory.service.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InventoryResponse(
        Long id,
        String uid,
        String storeId,
        String bookId,
        Integer quantity,
        LocalDateTime creationTime,
        LocalDateTime updatedAt
) {
}
