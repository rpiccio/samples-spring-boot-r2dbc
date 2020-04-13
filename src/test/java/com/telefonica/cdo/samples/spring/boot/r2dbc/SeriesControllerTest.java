package com.telefonica.cdo.samples.spring.boot.r2dbc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SeriesControllerTest extends TestSupport {

    @Test
    public void test() throws Exception {

        webClient.get().uri("/series/1").exchange().expectStatus().isOk().expectBody().consumeWith(x -> {

            try {

                ObjectMapper mapper = new ObjectMapper();

                Series series = mapper.readValue(x.getResponseBody(), Series.class);

                assertEquals("The Amazing Spider-Man", series.getName());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

}
