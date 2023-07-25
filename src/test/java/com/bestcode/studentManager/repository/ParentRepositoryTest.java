package com.bestcode.studentManager.repository;

import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static java.time.Month.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.jdbc.EmbeddedDatabaseConnection.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = H2)
class ParentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ParentRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldFindParentWithGivenEmail(){

        //Arrange, given
        Student student = new Student(1l,"umer","ali",
                "umer@gmail.com","231414142",
                LocalDate.of(2000, JANUARY,23),
                "Male",null);

        Parent parent =  new Parent(1l,"umer","ali",
                "umer@gmail.com","231414142",
                LocalDate.of(2000, JANUARY,23),
                "Male","Garada st",null);
    student.setParent(parent);
    parent.add(student);

    underTest.save(parent);

    //act
        Optional<Parent> actualValue = underTest.findByEmail(parent.getEmail());

    //Assert
        assertThat(actualValue).isNotEmpty();
        assertThat(actualValue.get().getEmail()).isEqualTo(parent.getEmail());

    }

}