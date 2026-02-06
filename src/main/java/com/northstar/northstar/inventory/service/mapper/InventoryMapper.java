package com.northstar.northstar.inventory.service.mapper;

import com.northstar.northstar.inventory.service.dto.InventoryRequest;
import com.northstar.northstar.inventory.service.dto.InventoryResponse;
import com.northstar.northstar.inventory.service.entity.InventoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InventoryMapper {

    InventoryEntity createEntityFromRequest(InventoryRequest request);

    void updateEntityFromRequest(InventoryRequest request, @MappingTarget InventoryEntity entity);

    InventoryResponse createResponseFromEntity(InventoryEntity entity);
}
