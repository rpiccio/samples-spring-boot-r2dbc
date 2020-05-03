package com.telefonica.cdo.samples.spring.boot.r2dbc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issues;

    public Flux<Issue> list() {
        return issues.findAll();
    }

    @Transactional
    public Flux<Issue> update(List<UpdateIssueRequest> updates) {

        return Flux.fromIterable(updates).flatMap(update ->
            issues.findById(update.getId()).map(issue ->
                issue.setBarcode(update.getBarcode())
            ).flatMap(issue ->
                issues.save(issue)
            )
        ).thenMany(issues.findAll());

    }

}
