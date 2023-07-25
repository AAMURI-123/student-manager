package com.bestcode.studentManager.Service;

import com.bestcode.studentManager.Dto.StudentDto;
import com.bestcode.studentManager.Exception.StudentAlreadyExistException;
import com.bestcode.studentManager.Exception.UserNotFoundException;
import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import com.bestcode.studentManager.repository.ParentRepository;
import com.bestcode.studentManager.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;


    @Override
    public Student createStudent(StudentDto studentDto) {

        Parent parent = studentDto.getParent();
        Student student = studentDto.getStudent();
        if(!studentRepository.findByEmail(student.getEmail()).isEmpty())
            throw new StudentAlreadyExistException("Student you are trying to save is already exist.");
        if(parentRepository.findByEmail(parent.getEmail()).isPresent())
            parent = parentRepository.findByEmail(parent.getEmail()).get();

        student.setParent(parent);
        parent.add(student);

        parentRepository.save(parent);

        return student;
    }

    @Override
    public List<Student> getAllStudents() {

        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(Long id) {

        return checkIfStudentWithIdExist(id) ;
    }


    @Override
    public Student updateStudent(StudentDto studentDto) {

        Parent parent = studentDto.getParent();
        Student student = studentDto.getStudent();

        student.setParent(parent);
        return studentRepository.save(student);
    }

    @Override
    public Parent updateParent(StudentDto studentDto) {
        Parent parent = studentDto.getParent();
        Student student = studentDto.getStudent();

        student.setParent(parent);
        parent.add(student);
        return parentRepository.save(parent);
    }

    @Override
    public void deleteStudentById(Long id) {
        checkIfStudentWithIdExist(id);
        studentRepository.deleteById(id);
    }

    public Student checkIfStudentWithIdExist(Long id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty())
            throw new UserNotFoundException("Student with id " + id +" Not Found");
        return student.get();
    }
}
