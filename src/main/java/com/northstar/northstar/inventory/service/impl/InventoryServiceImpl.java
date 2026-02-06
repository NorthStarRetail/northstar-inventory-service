package com.northstar.northstar.inventory.service.impl;

import com.northstar.northstar.inventory.service.dto.*;
import com.northstar.northstar.inventory.service.entity.InventoryEntity;
import com.northstar.northstar.inventory.service.exception.InsufficientStockException;
import com.northstar.northstar.inventory.service.exception.InventoryNotFoundException;
import com.northstar.northstar.inventory.service.exchange.BookExchangeService;
import com.northstar.northstar.inventory.service.exchange.StoreExchangeService;
import com.northstar.northstar.inventory.service.mapper.InventoryMapper;
import com.northstar.northstar.inventory.service.repository.InventoryRepository;
import com.northstar.northstar.inventory.service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;
    private final BookExchangeService bookExchangeService;
    private final StoreExchangeService storeExchangeService;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                InventoryMapper inventoryMapper,
                                BookExchangeService bookExchangeService,
                                StoreExchangeService storeExchangeService) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
        this.bookExchangeService = bookExchangeService;
        this.storeExchangeService = storeExchangeService;
    }

    private void validateRequest(InventoryRequest request) {
        if (request.getStoreId() == null || request.getStoreId().trim().isEmpty()) {
            throw new IllegalArgumentException("Store ID cannot be null or empty");
        }
        if (request.getBookId() == null || request.getBookId().trim().isEmpty()) {
            throw new IllegalArgumentException("Book ID cannot be null or empty");
        }
    }

    private void validateStoreAndBook(String storeId, String bookId) {
        log.debug("Validating store exists: {}", storeId);
        try {
            log.debug("Validating store exists: {}", storeId);
           StoreResponse storeResponse = storeExchangeService.getStore(storeId).getBody();
            log.debug("Store validated successfully: {}", storeResponse.getName());
        } catch (WebClientResponseException e) {
            log.error("Store validation failed for storeId: {} - Status: {}, Message: {}",
                    storeId, e.getStatusCode(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Store validation failed for storeId: {}", storeId, e);
            throw new RuntimeException("Failed to validate store: " + e.getMessage(), e);
        }

        log.debug("Validating book exists: {}", bookId);
        try {
            log.debug("Validating book exists: {}", bookId);
           BookResponse bookResponse = bookExchangeService.getBook(bookId).getBody();
            log.debug("Book validated successfully : {}", bookResponse.getTitle());
        } catch (WebClientResponseException e) {
            log.error("Book validation failed for bookId: {} - Status: {}, Message: {}",
                    bookId, e.getStatusCode(), e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Book validation failed for bookId: {}", bookId, e);
            throw new RuntimeException("Failed to validate book: " + e.getMessage(), e);
        }
        log.debug("Book validated successfully");
    }

    @Override
    @Transactional
    public InventoryResponse createInventory(InventoryRequest request) {
        validateRequest(request);
        log.debug("Creating inventory for storeId: {}, bookId: {}", request.getStoreId(), request.getBookId());

        validateStoreAndBook(request.getStoreId(), request.getBookId());

        if (inventoryRepository.findByStoreIdAndBookId(request.getStoreId(), request.getBookId()).isPresent()) {
            throw new IllegalArgumentException("Inventory already exists for store " + request.getStoreId() + " and book " + request.getBookId());
        }

        InventoryEntity inventoryEntity = InventoryEntity.builder()
                .storeId(request.getStoreId())
                .bookId(request.getBookId())
                .quantity(request.getQuantity())
                .build();
        InventoryEntity saved = inventoryRepository.save(inventoryEntity);
        return inventoryMapper.createResponseFromEntity(saved);
    }

    @Override
    @Transactional
    public InventoryResponse updateInventory(InventoryRequest request) {
        validateRequest(request);
        log.debug("Updating inventory for storeId: {}, bookId: {}", request.getStoreId(), request.getBookId());

        validateStoreAndBook(request.getStoreId(), request.getBookId());

        InventoryEntity entity = inventoryRepository.findByStoreIdAndBookId(request.getStoreId(), request.getBookId())
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for store " + request.getStoreId() + " and book " + request.getBookId()));

        entity.setQuantity(request.getQuantity());
        InventoryEntity saved = inventoryRepository.save(entity);
        return inventoryMapper.createResponseFromEntity(saved);
    }

    @Override
    public InventoryResponse getByUid(String uid) {
        return inventoryRepository.findByUid(uid)
                .map(inventoryMapper::createResponseFromEntity)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with uid: " + uid));
    }

    @Override
    public InventoryResponse getByStoreAndBook(String storeId, String bookId) {
        return inventoryRepository.findByStoreIdAndBookId(storeId, bookId)
                .map(inventoryMapper::createResponseFromEntity)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for store " + storeId + " and book " + bookId));
    }

    @Override
    public List<InventoryResponse> getByStoreId(String storeId, Pageable pageable) {
        return inventoryRepository.findByStoreId(storeId, pageable).getContent()
                .stream().map(inventoryMapper::createResponseFromEntity).toList();
    }

    @Override
    public List<InventoryResponse> getByBookId(String bookId, Pageable pageable) {
        return inventoryRepository.findByBookId(bookId, pageable).getContent()
                .stream().map(inventoryMapper::createResponseFromEntity).toList();
    }

    @Override
    @Transactional
    public void reserve(ReserveRequest request) {
        InventoryEntity entity = inventoryRepository.findForUpdateByStoreIdAndBookId(request.getStoreId(), request.getBookId())
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for store " + request.getStoreId() + " and book " + request.getBookId()));
        if (entity.getQuantity() < request.getQuantity()) {
            throw new InsufficientStockException("Insufficient stock. Available: " + entity.getQuantity() + ", requested: " + request.getQuantity());
        }
        entity.setQuantity(entity.getQuantity() - request.getQuantity());
        inventoryRepository.save(entity);
    }

    @Override
    @Transactional
    public void release(ReserveRequest request) {
        InventoryEntity entity = inventoryRepository.findForUpdateByStoreIdAndBookId(request.getStoreId(), request.getBookId())
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for store " + request.getStoreId() + " and book " + request.getBookId()));
        
        int newQuantity = entity.getQuantity() + request.getQuantity();
        entity.setQuantity(newQuantity);
        inventoryRepository.save(entity);
        log.debug("Released {} units for store {} book {}. Previous: {}, New: {}", 
                request.getQuantity(), request.getStoreId(), request.getBookId(), entity.getQuantity() - request.getQuantity(), newQuantity);
    }

    @Override
    public void deleteByUid(String uid) {
        InventoryEntity entity = inventoryRepository.findByUid(uid)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with uid: " + uid));
        inventoryRepository.delete(entity);
    }
}
