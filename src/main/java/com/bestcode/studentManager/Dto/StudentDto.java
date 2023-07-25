package com.bestcode.studentManager.Dto;

import com.bestcode.studentManager.model.Parent;
import com.bestcode.studentManager.model.Student;
import lombok.Data;

@Data
public class StudentDto {

    private Student student;
    private Parent parent;
}
