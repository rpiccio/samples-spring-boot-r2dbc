package com.telefonica.cdo.samples.spring.boot.r2dbc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IssueControllerTestUtils {

    public static void test(WebTestClient webClient) throws Exception {

        UpdateIssueRequest update1 = new UpdateIssueRequest();

        update1.setId(1L);
        update1.setBarcode("00000000000000000");

        UpdateIssueRequest update2 = new UpdateIssueRequest();

        update2.setId(2L);
        update2.setBarcode("111111111111111111");

        List<UpdateIssueRequest> updates = new ArrayList<>();

        updates.add(update1);
        updates.add(update2);

        webClient.patch()
            .uri("/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(updates))
            .exchange()
            .expectStatus()
            .is5xxServerError();

        webClient.get().uri("/issues").exchange().expectStatus().isOk().expectBody().consumeWith(x -> {

            try {

                ObjectMapper mapper = new ObjectMapper();

                JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Issue.class);

                List<Issue> issues = mapper.readValue(x.getResponseBody(), type);

                for (Issue issue : issues) {

                    if (issue.getId().equals(1L)) {
                        assertEquals("75960608936901611", issue.getBarcode());
                    }

                    if (issue.getId().equals(2L)) {
                        assertEquals("75960609406600111", issue.getBarcode());
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

}
