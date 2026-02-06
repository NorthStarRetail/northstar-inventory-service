package com.northstar.northstar.inventory.service.exchange;

import com.northstar.northstar.inventory.service.dto.BookResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange("/api/book")
public interface BookExchangeService {

    @GetExchange("/v1/books/{id}")
     ResponseEntity<BookResponse> getBook(@PathVariable String id);
}
