package com.telefonica.cdo.samples.spring.boot.r2dbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class SeriesService {

    @Autowired
    private SeriesRepository series;

    public Mono<Series> get(Long id) {
        return series.findById(id);
    }

}
