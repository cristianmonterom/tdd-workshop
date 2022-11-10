package com.workshop.tddworkshop.controllers;

import com.github.javafaker.Faker;
import com.workshop.tddworkshop.controllers.dto.StudentDTO;
import com.workshop.tddworkshop.model.Student;
import com.workshop.tddworkshop.repository.StudentRepository;
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

import java.util.Arrays;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@ActiveProfiles("tests")
public class BecaEstudianteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    private Faker faker = new Faker();
    private HttpEntity<String> request;

    @BeforeEach
    void setUp() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        request = new HttpEntity<>(headers);
    }


    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void shouldReturnStudentInformationWhenStudentIdIsValid() {
        Student expectedStudent = new Student(12L, faker.name().firstName());
        studentRepository.save(expectedStudent);

        ResponseEntity<StudentDTO> actualResponse = this.restTemplate.exchange("/v1/becas/alumno/12", HttpMethod.GET, request, StudentDTO.class);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        Assertions.assertEquals(expectedStudent.getId(), actualResponse.getBody().getId());
        Assertions.assertEquals(expectedStudent.getName(), actualResponse.getBody().getName());
    }

    @Test
    void shouldNotReturnStudentInformationWhenStudentIdDoesNotExists(){
        studentRepository.save(new Student(12L, faker.name().firstName()));

        ResponseEntity<StudentDTO> actualResponse = this.restTemplate.exchange("/v1/becas/alumno/13", HttpMethod.GET, request, StudentDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode() );
        Assertions.assertNull(actualResponse.getBody());
    }

}
