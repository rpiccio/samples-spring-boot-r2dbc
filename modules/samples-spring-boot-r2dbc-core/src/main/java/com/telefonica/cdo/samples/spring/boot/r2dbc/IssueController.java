package com.telefonica.cdo.samples.spring.boot.r2dbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class IssueController {

    @Autowired
    private IssueService service;

    @GetMapping(path = "/issues", produces = "application/json")
    public Flux<Issue> list() {
        return service.list();
    }

    @PatchMapping(path = "/issues", consumes = "application/json", produces = "application/json")
    public Flux<Issue> update(@RequestBody List<UpdateIssueRequest> updates) {
        return service.update(updates);
    }

}
