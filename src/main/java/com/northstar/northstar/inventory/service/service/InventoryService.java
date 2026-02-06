package com.northstar.northstar.inventory.service.service;

import com.northstar.northstar.inventory.service.dto.InventoryRequest;
import com.northstar.northstar.inventory.service.dto.InventoryResponse;
import com.northstar.northstar.inventory.service.dto.ReserveRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    InventoryResponse createInventory(InventoryRequest request);

    InventoryResponse updateInventory(InventoryRequest request);

    InventoryResponse getByUid(String uid);

    InventoryResponse getByStoreAndBook(String storeId, String bookId);

    List<InventoryResponse> getByStoreId(String storeId, Pageable pageable);

    List<InventoryResponse> getByBookId(String bookId, Pageable pageable);

    void reserve(ReserveRequest request);

    void release(ReserveRequest request);

    void deleteByUid(String uid);
}
