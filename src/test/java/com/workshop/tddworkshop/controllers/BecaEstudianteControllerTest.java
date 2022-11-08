package com.workshop.tddworkshop.controllers;

import com.github.javafaker.Faker;
import com.workshop.tddworkshop.controllers.dto.StudentDTO;
import com.workshop.tddworkshop.model.Student;
import com.workshop.tddworkshop.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
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

    private Faker faker = new Faker();

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void shouldReturnStudentInformationWhenStudentIdIsValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);

        Long expectedStudentId  = 12L;
        Student expectedStudent = new Student(expectedStudentId, faker.name().firstName());
        studentRepository.save(expectedStudent);

        ResponseEntity<StudentDTO> actualResponse = this.restTemplate.exchange("/v1/becas/alumno/12", HttpMethod.GET, request, StudentDTO.class);

        Assertions.assertEquals(HttpStatus.OK, actualResponse.getStatusCode() );
        Assertions.assertEquals(expectedStudentId, actualResponse.getBody().getId());
    }

    @Test
    void shouldNotReturnStudentInformationWhenStudentIdDoesNotExists(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);

        Long expectedStudentId  = 12L;
        Student expectedStudent = new Student(expectedStudentId, faker.name().firstName());
        studentRepository.save(expectedStudent);

        ResponseEntity<StudentDTO> actualResponse = this.restTemplate.exchange("/v1/becas/alumno/13", HttpMethod.GET, request, StudentDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, actualResponse.getStatusCode() );
        Assertions.assertNull(actualResponse.getBody());
    }

}
