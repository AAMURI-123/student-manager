package com.bestcode.studentManager.Controller;

import com.bestcode.studentManager.Dto.StudentDto;
import com.bestcode.studentManager.Service.StudentServiceImpl;
import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static java.time.Month.JANUARY;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @MockBean
    private StudentServiceImpl studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

   @Test
    void itShouldCreateNewStudent() throws Exception {
       Student student = new Student(1l, "umer", "ali",
               "umer@gmail.com", "231414142",
               LocalDate.of(2000, JANUARY, 23),
               "Male",  null);

       Parent parent = new Parent(1l, "umer", "ali",
               "umer@gmail.com", "231414142",
               LocalDate.of(2000, JANUARY, 23),
               "Male", "Garada st",  null);

       StudentDto studentDto = new StudentDto();
       studentDto.setStudent(student);
       studentDto.setParent(parent);

       //student.setParent(parent);
       when(studentService.createStudent(studentDto)).thenReturn(student);

       mockMvc.perform(post("/student/create")
                   .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(studentDto)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andExpect(jsonPath("$.email").value(student.getEmail()))
                    .andExpect(jsonPath("$.firstName").value(student.getFirstName()));

       ArgumentCaptor<StudentDto> argumentCaptor = ArgumentCaptor.forClass(StudentDto.class);
       verify(studentService).createStudent(argumentCaptor.capture());
       StudentDto captorValue = argumentCaptor.getValue();
       assertThat(captorValue).isEqualTo(studentDto);


   }

   @Test
    void itShouldGetAllStudent() throws Exception {
       mockMvc.perform(get("/student"))
                .andExpect(status().isOk());

       verify(studentService).getAllStudents();
   }

   @Test
    void itShouldGetStudentById() throws Exception {
       Student student = new Student(1l, "umer", "ali",
               "umer@gmail.com", "231414142",
               LocalDate.of(2000, JANUARY, 23),
               "Male",  null);
       when(studentService.getStudentById(student.getId())).thenReturn(student);

       mockMvc.perform(get("/student/{id}",student.getId()))
                .andExpect(status().isOk())
               .andDo(print())
                .andExpect(jsonPath("$.email").value(student.getEmail()));

       ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
       verify(studentService).getStudentById(argumentCaptor.capture());
       Long captorValue = argumentCaptor.getValue();
       assertThat(captorValue).isEqualTo(student.getId());
   }

   @Test
    void itShouldUpdateStudent() throws Exception{
       Student student = new Student(1l, "umer", "ali",
               "umer@gmail.com", "231414142",
               LocalDate.of(2000, JANUARY, 23),
               "Male",  null);

       Parent parent = new Parent(1l, "umer", "ali",
               "umer@gmail.com", "231414142",
               LocalDate.of(2000, JANUARY, 23),
               "Male", "Garada st",  null);

       StudentDto studentDto = new StudentDto();
       studentDto.setStudent(student);
       studentDto.setParent(parent);

       //student.setParent(parent);
       when(studentService.updateStudent(studentDto)).thenReturn(student);

       mockMvc.perform(put("/student/update")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(studentDto)))
               .andExpect(status().isOk())
               .andDo(print())
       .andExpect(jsonPath("$.email").value(student.getEmail()))
       .andExpect(jsonPath("$.firstName").value(student.getFirstName()));

       ArgumentCaptor<StudentDto> argumentCaptor = ArgumentCaptor.forClass(StudentDto.class);
       verify(studentService).updateStudent(argumentCaptor.capture());
       StudentDto captorValue = argumentCaptor.getValue();
       assertThat(captorValue).isEqualTo(studentDto);

   }

    @Test
    void itShouldUpdateParent() throws Exception{
        Student student = new Student(1l, "umer", "ali",
                "umer@gmail.com", "231414142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

        Parent parent = new Parent(1l, "umer", "ali",
                "umer@gmail.com", "231414142",
                LocalDate.of(2000, JANUARY, 23),
                "Male", "Garada st",  null);

        StudentDto studentDto = new StudentDto();
        studentDto.setStudent(student);
        studentDto.setParent(parent);

        //student.setParent(parent);
        when(studentService.updateParent(studentDto)).thenReturn(parent);

        mockMvc.perform(put("/student/parent/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.email").value(parent.getEmail()))
                .andExpect(jsonPath("$.firstName").value(parent.getFirstName()));

        ArgumentCaptor<StudentDto> argumentCaptor = ArgumentCaptor.forClass(StudentDto.class);
        verify(studentService).updateParent(argumentCaptor.capture());
        StudentDto captorValue = argumentCaptor.getValue();
        assertThat(captorValue).isEqualTo(studentDto);

    }


    @Test
    void itShouldDeleteStudentById() throws Exception {

        Student student = new Student(1l, "umer", "ali",
                "umer@gmail.com", "231414142",
                LocalDate.of(2000, JANUARY, 23),
                "Male",  null);

        doNothing().when(studentService).deleteStudentById(student.getId());

        mockMvc.perform(delete("/student/{id}",student.getId()))
                .andExpect(status().isOk());
        ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
        verify(studentService).deleteStudentById(argumentCaptor.capture());
        Long captorValue = argumentCaptor.getValue();
        assertThat(captorValue).isEqualTo(student.getId());
    }

    }