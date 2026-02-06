package com.northstar.northstar.inventory.service.mapper;

import com.northstar.northstar.inventory.service.dto.InventoryRequest;
import com.northstar.northstar.inventory.service.dto.InventoryResponse;
import com.northstar.northstar.inventory.service.entity.InventoryEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-06T14:11:20+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.4 (Azul Systems, Inc.)"
)
@Component
public class InventoryMapperImpl implements InventoryMapper {

    @Override
    public InventoryEntity createEntityFromRequest(InventoryRequest request) {
        if ( request == null ) {
            return null;
        }

        InventoryEntity.InventoryEntityBuilder inventoryEntity = InventoryEntity.builder();

        inventoryEntity.storeId( request.getStoreId() );
        inventoryEntity.bookId( request.getBookId() );
        inventoryEntity.quantity( request.getQuantity() );

        return inventoryEntity.build();
    }

    @Override
    public void updateEntityFromRequest(InventoryRequest request, InventoryEntity entity) {
        if ( request == null ) {
            return;
        }

        entity.setStoreId( request.getStoreId() );
        entity.setBookId( request.getBookId() );
        entity.setQuantity( request.getQuantity() );
    }

    @Override
    public InventoryResponse createResponseFromEntity(InventoryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        InventoryResponse.InventoryResponseBuilder inventoryResponse = InventoryResponse.builder();

        inventoryResponse.id( entity.getId() );
        inventoryResponse.uid( entity.getUid() );
        inventoryResponse.storeId( entity.getStoreId() );
        inventoryResponse.bookId( entity.getBookId() );
        inventoryResponse.quantity( entity.getQuantity() );
        inventoryResponse.creationTime( entity.getCreationTime() );
        inventoryResponse.updatedAt( entity.getUpdatedAt() );

        return inventoryResponse.build();
    }
}
