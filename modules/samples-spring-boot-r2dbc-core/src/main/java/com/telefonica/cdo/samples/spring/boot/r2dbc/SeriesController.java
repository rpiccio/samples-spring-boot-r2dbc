package com.telefonica.cdo.samples.spring.boot.r2dbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class SeriesController {

    @Autowired
    private SeriesService service;

    @GetMapping(path = "/series/{id}", produces = "application/json")
    public Mono<Series> get(@PathVariable Long id) {
        return service.get(id);
    }

}
