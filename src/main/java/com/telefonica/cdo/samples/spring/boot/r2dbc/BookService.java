package com.telefonica.cdo.samples.spring.boot.r2dbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Flux<Book> list() {
        return repository.findAll();
    }

    public Flux<Book> update(List<UpdateBookRequest> updates) throws Exception {

        return Flux.fromIterable(updates).flatMap(update -> {
            return repository.findById(update.getId()).map(book -> {
                return book.setIsbn(update.getIsbn());
            }).flatMap(book -> {
                return repository.save(book);
            });
        }).thenMany(repository.findAll());

    }

    @Transactional
    public Flux<Book> updateTransactional(List<UpdateBookRequest> updates) throws Exception {
        return update(updates);
    }

}
