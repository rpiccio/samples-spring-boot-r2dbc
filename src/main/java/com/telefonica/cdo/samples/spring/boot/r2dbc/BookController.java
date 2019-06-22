package com.telefonica.cdo.samples.spring.boot.r2dbc;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class BookController {

    @Autowired
    private BookService service;

    @GetMapping(path = "/books", produces = "application/json")
    public Flux<Book> list() {
        return service.list();
    }

    @PatchMapping(path = "/books", consumes = "application/json", produces = "application/json")
    public Flux<Book> update(@RequestBody ArrayList<UpdateBookRequest> updates) throws Exception {
        return service.update(updates);
    }

    @PatchMapping(path = "/booksT", consumes = "application/json", produces = "application/json")
    public Flux<Book> updateTransactional(@RequestBody ArrayList<UpdateBookRequest> updates) throws Exception {
        return service.updateTransactional(updates);
    }

}
