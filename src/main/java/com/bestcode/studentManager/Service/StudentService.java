package com.bestcode.studentManager.Service;

import com.bestcode.studentManager.Dto.StudentDto;
import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;

import java.util.List;

public interface StudentService {

    public List<Student> getAllStudents();
    public Student getStudentById(Long id);
    public Student createStudent(StudentDto studentDto);
    public  Student updateStudent(StudentDto studentDto);
    public Parent updateParent(StudentDto studentDto);
    public void deleteStudentById(Long id);

}
