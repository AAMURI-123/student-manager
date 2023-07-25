package com.bestcode.studentManager.Service;

import com.bestcode.studentManager.Dto.StudentDto;
import com.bestcode.studentManager.Exception.StudentAlreadyExistException;
import com.bestcode.studentManager.Exception.UserNotFoundException;
import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import com.bestcode.studentManager.repository.ParentRepository;
import com.bestcode.studentManager.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static java.time.Month.JANUARY;
import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ParentRepository parentRepository;

    @InjectMocks
    private StudentServiceImpl underTest;

    private Student student;
    private Parent parent;

    @BeforeEach
    void setUp() {

         student = new Student(1l, "umer", "ali",
                "umer@gmail.com", "231414142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

         parent = new Parent(1l, "umer", "ali",
                "umer@gmail.com", "231414142",
                LocalDate.of(2000, JANUARY, 23),
                "Male", "Garada st", null);
    }

    @Test
    void itShouldGetAllStudent(){
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }

    @Test
    void itShouldThrowIfUserWithIdDoesNotExist(){
        //when
        //then
        assertThatThrownBy(()->underTest.checkIfStudentWithIdExist(1l))
                                .isInstanceOf(UserNotFoundException.class)
                                .hasMessage("Student with id " + 1 +" Not Found");
    }

    @Test
    void itShouldNotThrowIfUserWithIdExist(){


        when(studentRepository.findById(student.getId())).thenReturn(of(student));

       //when
       //then
        assertDoesNotThrow(()-> underTest.checkIfStudentWithIdExist(student.getId()));
    }

    @Test
    void itShouldGetStudentById(){


        when(studentRepository.findById(student.getId())).thenReturn(of(student));
        //when
        underTest.getStudentById(student.getId());
        //then
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentRepository).findById(argumentCaptor.capture());
        Long captorValue = argumentCaptor.getValue();
        assertThat(captorValue).isEqualTo(student.getId());
    }

    @Test
    void itShouldUpdateStudent(){
        student.setParent(parent);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(student);
        studentDto.setParent(parent);

        when(studentRepository.save(student)).thenReturn(student);

        //when
        Student actualValue = underTest.updateStudent(studentDto);

        //then
        ArgumentCaptor<Student> argumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(argumentCaptor.capture());
        Student captorValue = argumentCaptor.getValue();
        assertAll(()->assertThat(actualValue).isNotNull(),
                 ()->assertThat(studentDto.getStudent()).isNotNull(),
                 ()->assertThat(studentDto.getParent()).isNotNull(),
                 ()->assertThat(captorValue.getParent()).isNotNull(),
                 ()->assertThat(captorValue.getParent()).isEqualTo(parent));

    }

    @Test
    void itShouldUpdateParent(){
        student.setParent(parent);
        parent.add(student);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(student);
        studentDto.setParent(parent);

        when(parentRepository.save(parent)).thenReturn(parent);

        //when
        Parent actualValue = underTest.updateParent(studentDto);

        //then
        ArgumentCaptor<Parent> argumentCaptor = ArgumentCaptor.forClass(Parent.class);
        verify(parentRepository).save(argumentCaptor.capture());
        Parent captorValue = argumentCaptor.getValue();
        assertAll(()->assertThat(actualValue).isNotNull(),
                ()->assertThat(studentDto.getStudent()).isNotNull(),
                ()->assertThat(studentDto.getParent()).isNotNull(),
                ()->assertThat(captorValue.getStudents()).isNotNull());
    }

        @Test
        void itShouldDeleteStudentById(){

            when(studentRepository.findById(student.getId())).thenReturn(of(student));

            // act
            underTest.deleteStudentById(student.getId());

            //then
            ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
            verify(studentRepository).deleteById(argumentCaptor.capture());
            Long captorValue = argumentCaptor.getValue();
            assertThat(captorValue).isEqualTo(student.getId());
        }

        @Test
    void itShouldThrowWhenWeTryToSaveStudentThatAlreadyExist(){

            student.setParent(parent);
            parent.add(student);

            StudentDto studentDto = new StudentDto();
            studentDto.setStudent(student);
            studentDto.setParent(parent);

            when(studentRepository.findByEmail(student.getEmail())).thenReturn(of(student));

            //Act
            //then
           assertThatThrownBy(()->underTest.createStudent(studentDto))
                                .isInstanceOf(StudentAlreadyExistException.class)
                                .hasMessage("Student you are trying to save is already exist.");

        }

    @Test
    void itShouldCreateNewStudent(){

        student.setParent(parent);
        parent.add(student);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(student);
        studentDto.setParent(parent);

        when(studentRepository.findByEmail(student.getEmail())).thenReturn(empty());
        when(parentRepository.findByEmail(parent.getEmail())).thenReturn(of(parent));

        Student responseStudent = underTest.createStudent(studentDto);

        ArgumentCaptor<Parent> argumentCaptor = ArgumentCaptor.forClass(Parent.class);
        verify(parentRepository).save(argumentCaptor.capture());
        Parent captorValue = argumentCaptor.getValue();
        assertThat(captorValue.getEmail()).isEqualTo(studentDto.getParent().getEmail());
        assertThat(responseStudent.getEmail()).isEqualTo(student.getEmail());
    }
}