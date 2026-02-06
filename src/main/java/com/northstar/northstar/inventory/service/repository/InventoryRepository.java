package com.northstar.northstar.inventory.service.repository;

import com.northstar.northstar.inventory.service.entity.InventoryEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    Optional<InventoryEntity> findByUid(String uid);

    Page<InventoryEntity> findByStoreId(String storeId, Pageable pageable);

    Page<InventoryEntity> findByBookId(String bookId, Pageable pageable);

    Optional<InventoryEntity> findByStoreIdAndBookId(String storeId, String bookId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM InventoryEntity i WHERE i.storeId = :storeId AND i.bookId = :bookId")
    Optional<InventoryEntity> findForUpdateByStoreIdAndBookId(@Param("storeId") String storeId, @Param("bookId") String bookId);
}
