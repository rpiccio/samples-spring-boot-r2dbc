package com.telefonica.cdo.samples.spring.boot.r2dbc;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.testcontainers.containers.PostgreSQLContainer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureWebTestClient
@ContextConfiguration(initializers = {BookControllerTest.Initializer.class})
@RunWith(SpringRunner.class)
@SpringBootTest
public class BookControllerTest {

    @ClassRule
    public static PostgreSQLContainer<?> db = new PostgreSQLContainer<>("postgres:10");

    @Autowired
    private WebTestClient webClient;

    @Test
    public void test() throws Exception {

        UpdateBookRequest update1 = new UpdateBookRequest();

        update1.setId(1L);
        update1.setIsbn("0123456789000");

        UpdateBookRequest update2 = new UpdateBookRequest();

        update2.setId(2L);
        update2.setIsbn("01234567890001");

        List<UpdateBookRequest> updates = new ArrayList<>();

        updates.add(update1);
        updates.add(update2);

        webClient.patch()
            .uri("/books")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(updates))
            .exchange()
            .expectStatus()
            .is5xxServerError();

        webClient.get().uri("/books").exchange().expectStatus().isOk().expectBody().consumeWith(x -> {

            try {

                ObjectMapper mapper = new ObjectMapper();

                JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);

                List<Book> books = mapper.readValue(x.getResponseBody(), type);

                for (Book book : books) {

                    if (book.getId().equals(1L)) {
                        assertEquals("0123456789000", book.getIsbn());
                    }

                    if (book.getId().equals(2L)) {
                        assertEquals("9780201633610", book.getIsbn());
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    @Test
    public void testTransactional() throws Exception {

        UpdateBookRequest update1 = new UpdateBookRequest();

        update1.setId(2L);
        update1.setIsbn("0123456789000");

        UpdateBookRequest update2 = new UpdateBookRequest();

        update2.setId(3L);
        update2.setIsbn("01234567890001");

        List<UpdateBookRequest> updates = new ArrayList<>();

        updates.add(update1);
        updates.add(update2);

        webClient.patch()
            .uri("/booksT")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromObject(updates))
            .exchange()
            .expectStatus()
            .is5xxServerError();

        webClient.get().uri("/books").exchange().expectStatus().isOk().expectBody().consumeWith(x -> {

            try {

                ObjectMapper mapper = new ObjectMapper();

                JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, Book.class);

                List<Book> books = mapper.readValue(x.getResponseBody(), type);

                for (Book book : books) {

                    if (book.getId().equals(2L)) {
                        assertEquals("9780201633610", book.getIsbn());
                    }

                    if (book.getId().equals(3L)) {
                        assertEquals("9780321200686", book.getIsbn());
                    }

                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of(
                "spring.datasource.url=" + buildJdbcUrl(),
                "spring.datasource.username=" + db.getUsername(),
                "spring.datasource.password=" + db.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());

            TestPropertyValues.of(
                "spring.r2dbc.url=" + buildR2dbcUrl(),
                "spring.r2dbc.username=" + db.getUsername(),
                "spring.r2dbc.password=" + db.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());

        }

        private String buildJdbcUrl() {
            return String.format("jdbc:postgresql://%s:%d/%s", db.getContainerIpAddress(), db.getMappedPort(5432), db.getDatabaseName());
        }

        private String buildR2dbcUrl() {
            return String.format("r2dbc:postgresql://%s:%d/%s", db.getContainerIpAddress(), db.getMappedPort(5432), db.getDatabaseName());
        }

    }

}
