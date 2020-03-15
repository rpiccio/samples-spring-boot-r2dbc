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

    public Flux<Book> update(List<UpdateBookRequest> updates) {

        return Flux.fromIterable(updates).flatMap(update ->
            repository.findById(update.getId()).map(book ->
                book.setIsbn(update.getIsbn())
            ).flatMap(book ->
                repository.save(book)
            )
        ).thenMany(repository.findAll());

    }

    @Transactional
    public Flux<Book> updateTransactional(List<UpdateBookRequest> updates) {
        return update(updates);
    }

}
