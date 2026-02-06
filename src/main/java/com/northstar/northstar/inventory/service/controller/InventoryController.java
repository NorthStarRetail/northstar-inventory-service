package com.northstar.northstar.inventory.service.controller;

import com.northstar.northstar.inventory.service.dto.InventoryRequest;
import com.northstar.northstar.inventory.service.dto.InventoryResponse;
import com.northstar.northstar.inventory.service.dto.ReserveRequest;
import com.northstar.northstar.inventory.service.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/inventory")
@Tag(name = "Inventory", description = "Inventory Management API")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Operation(summary = "Create inventory")
    @PostMapping
    public ResponseEntity<InventoryResponse> create(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(request));
    }

    @Operation(summary = "Update inventory")
    @PutMapping
    public ResponseEntity<InventoryResponse> update(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.updateInventory(request));
    }

    @Operation(summary = "Get inventory by ID")
    @GetMapping("/{id}")
    public ResponseEntity<InventoryResponse> getById(@PathVariable String id) {
        return ResponseEntity.ok(inventoryService.getByUid(id));
    }

    @Operation(summary = "Get inventory by store and book")
    @GetMapping("/store/{storeId}/book/{bookId}")
    public ResponseEntity<InventoryResponse> getByStoreAndBook(@PathVariable String storeId, @PathVariable String bookId) {
        return ResponseEntity.ok(inventoryService.getByStoreAndBook(storeId, bookId));
    }

    @Operation(summary = "Get inventory by store")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryResponse>> getByStoreId(@PathVariable String storeId,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inventoryService.getByStoreId(storeId, PageRequest.of(page, size)));
    }

    @Operation(summary = "Get inventory by book")
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<InventoryResponse>> getByBookId(@PathVariable String bookId,
                                                               @RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(inventoryService.getByBookId(bookId, PageRequest.of(page, size)));
    }

    @Operation(summary = "Reserve stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Stock reserved"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Inventory not found")
    })
    @PostMapping("/reserve")
    public ResponseEntity<Void> reserve(@Valid @RequestBody ReserveRequest request) {
        inventoryService.reserve(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Release stock")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Stock reserved"),
            @ApiResponse(responseCode = "400", description = "Insufficient stock"),
            @ApiResponse(responseCode = "404", description = "Inventory not found")
    })
    @PostMapping("/release")
    public ResponseEntity<Void> release(@Valid @RequestBody ReserveRequest request) {
        inventoryService.release(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete inventory")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        inventoryService.deleteByUid(id);
        return ResponseEntity.noContent().build();
    }
}
