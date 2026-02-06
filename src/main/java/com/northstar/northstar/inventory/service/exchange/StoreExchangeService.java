package com.northstar.northstar.inventory.service.exchange;

import com.northstar.northstar.inventory.service.dto.StoreResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/store")
public interface StoreExchangeService {

    @GetExchange("/v1/stores/{id}")
    ResponseEntity<StoreResponse> getStore(@PathVariable String id);
}
