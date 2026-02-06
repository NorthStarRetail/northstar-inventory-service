package com.northstar.northstar.inventory.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String uid;
    private String title;
    private String isbn;
    private String author;
    private BigDecimal price;
    private String status;
    private LocalDateTime creationTime;
}
