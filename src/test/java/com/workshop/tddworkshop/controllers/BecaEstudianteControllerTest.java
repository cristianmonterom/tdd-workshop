package com.workshop.tddworkshop.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@ActiveProfiles("tests")
public class BecaEstudianteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnStudentInformationWhenStudentIdIsValid() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<String> request = new HttpEntity<>(headers);

        ResponseEntity<String> actualResponse = this.restTemplate.exchange("/v1/becas/alumno/", HttpMethod.GET, request, String.class);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode() );
        Assertions.assertEquals("Fernanda", actualResponse.getBody());

    }
}
