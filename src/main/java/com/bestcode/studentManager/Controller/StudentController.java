package com.bestcode.studentManager.Controller;

import com.bestcode.studentManager.Dto.StudentDto;
import com.bestcode.studentManager.Service.StudentServiceImpl;
import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {


    private final StudentServiceImpl studentService;

    @PostMapping("/create")
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto studentDto){
        return  new ResponseEntity<>(studentService.createStudent(studentDto), CREATED);
    }

    @GetMapping
    @ResponseStatus(OK)
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){
        return  new ResponseEntity<>(studentService.getStudentById(id), OK);
    }

    @PutMapping("update")
    public  ResponseEntity<Student> updateStudent(@RequestBody StudentDto studentDto){
        return new ResponseEntity<>(studentService.updateStudent(studentDto),OK);
    }

    @PutMapping("/parent/update")
    public  ResponseEntity<Parent> updateParent(@RequestBody StudentDto studentDto){
        return new ResponseEntity<>(studentService.updateParent(studentDto),OK);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(value = OK)
    public void deleteStudentById(@PathVariable("id") Long id){
        studentService.deleteStudentById(id);
    }
}
