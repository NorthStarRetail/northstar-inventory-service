package com.northstar.northstar.inventory.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreResponse {
    private Long id;
    private String uid;
    private String name;
    private String address;
    private String region;
    private LocalDateTime creationTime;
}
