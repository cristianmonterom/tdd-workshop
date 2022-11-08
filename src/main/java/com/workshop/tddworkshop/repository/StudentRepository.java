package com.workshop.tddworkshop.repository;

import com.workshop.tddworkshop.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {

}
