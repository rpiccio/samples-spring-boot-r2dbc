package com.telefonica.cdo.samples.spring.boot.r2dbc.mysql;

import org.junit.ClassRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MySQLContainer;

@AutoConfigureWebTestClient
@ContextConfiguration(initializers = {TestSupport.Initializer.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringRunner.class)
@SpringBootTest
public abstract class TestSupport {

    @ClassRule
    public static MySQLContainer<?> database = new MySQLContainer<>("mysql:8");

    @Autowired
    protected WebTestClient webClient;

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            TestPropertyValues.of(
                "spring.datasource.url=" + buildJdbcUrl(),
                "spring.datasource.username=" + database.getUsername(),
                "spring.datasource.password=" + database.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());

            TestPropertyValues.of(
                "spring.flyway.url=" + buildJdbcUrl(),
                "spring.flyway.username=" + database.getUsername(),
                "spring.flyway.password=" + database.getPassword(),
                "spring.flyway.locations=" + "classpath:db/migration/mysql"
            ).applyTo(configurableApplicationContext.getEnvironment());

            TestPropertyValues.of(
                "spring.r2dbc.url=" + buildR2dbcUrl(),
                "spring.r2dbc.username=" + database.getUsername(),
                "spring.r2dbc.password=" + database.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());

        }

        private String buildJdbcUrl() {
            return String.format("jdbc:mysql://%s:%d/%s", database.getContainerIpAddress(), database.getMappedPort(3306), database.getDatabaseName());
        }

        private String buildR2dbcUrl() {
            return String.format("r2dbc:mysql://%s:%d/%s", database.getContainerIpAddress(), database.getMappedPort(3306), database.getDatabaseName());
        }

    }

}
