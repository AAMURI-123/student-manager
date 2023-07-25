package com.bestcode.studentManager.Integration;

import com.bestcode.studentManager.Controller.StudentController;
import com.bestcode.studentManager.Dto.StudentDto;
import com.bestcode.studentManager.Service.StudentServiceImpl;
import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import com.bestcode.studentManager.repository.ParentRepository;
import com.bestcode.studentManager.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost:";

    private static RestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @BeforeAll
    static void beforeAll() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl + port +  "/student";
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldCreateNewStudent(){
        Student student = new Student( "umer", "ali",
                "umer1@gmail.com", "28844142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

        Parent parent = new Parent( "aamir", "ali",
                "aamir1@gmail.com", "23141416",
                LocalDate.of(2000, JANUARY, 23),
                "Male", "Garada st",  null);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(student);
        studentDto.setParent(parent);

        //when
        Student responseStudent = restTemplate.postForObject(baseUrl + "/create", studentDto, Student.class);
        System.out.println(responseStudent.toString());
        //then
        assertThat(responseStudent).isNotNull();
        assertThat(responseStudent.getEmail()).isEqualTo(student.getEmail());
    }

    @Test
    void itShouldGetAllStudent(){
        //given
        Student umer = new Student( "umer", "ali",
                "umer1@gmail.com", "28844142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

        Parent aamir = new Parent("aamir", "ali",
                "aamir1@gmail.com", "23141416",
                LocalDate.of(2000, JANUARY, 23),
                "Male", "Garada st",  null);

        Student alex = new Student("alex", "li",
                "alex1@gmail.com", "28844142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

        Parent mariam = new Parent( "mariam", "bo",
                "aamir1@gmail.com", "23141416",
                LocalDate.of(2000, JANUARY, 23),
                "Male", "Garada st",  null);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(umer);
        studentDto.setParent(aamir);

        StudentDto studentDto2 = new StudentDto();
        studentDto.setStudent(alex);
        studentDto.setParent(mariam);

        Student responseUmer = restTemplate.postForObject(baseUrl + "/create", studentDto, Student.class);
        Student responseAlex = restTemplate.postForObject(baseUrl + "/create", studentDto2, Student.class);

        //when
        List<Student> responseList = restTemplate.getForObject(baseUrl , List.class);

        //then
        assertThat(responseList.size()).isEqualTo(2);
    }

    @Test
    void itShouldGetStudentById(){
        //given
        Student umer = new Student( "umer", "ali",
                "umer1@gmail.com", "28844142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

        Parent aamir = new Parent("aamir", "ali",
                "aamir1@gmail.com", "23141416",
                LocalDate.of(2000, JANUARY, 23),
                "Male", "Garada st",  null);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(umer);
        studentDto.setParent(aamir);

        Student responseUmer = restTemplate.postForObject(baseUrl + "/create", studentDto, Student.class);
        System.out.println(responseUmer.toString());
        //when
        Student actualStudent = restTemplate.getForObject(baseUrl + "/" + responseUmer.getId(), Student.class);

        //then
        assertThat(actualStudent).isNotNull();
        assertThat(actualStudent.getEmail()).isEqualTo(umer.getEmail());
    }




}
