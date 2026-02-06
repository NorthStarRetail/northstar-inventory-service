package com.northstar.northstar.inventory.service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "ns_inventory", uniqueConstraints = @UniqueConstraint(columnNames = {"store_uid", "book_uid"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false, length = 36)
    private String uid;

    @Column(name = "store_uid", nullable = false)
    private String storeId;

    @Column(name = "book_uid", nullable = false)
    private String bookId;

    @Column(nullable = false)
    private Integer quantity;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationTime;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @PrePersist
    private void generateUid() {
        if (uid == null) {
            uid = UUID.randomUUID().toString();
        }
    }
}
