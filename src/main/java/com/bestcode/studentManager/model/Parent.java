package com.bestcode.studentManager.model;

import com.bestcode.studentManager.enumratoin.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Parent implements Serializable {
    public Parent(String firstName, String lastName, String email, String phoneNo, LocalDate dob, String gender, String address, List<Student> students) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.students = students;
    }

    @Id
    @SequenceGenerator(name = "Generate_Seq" , sequenceName = "Generate_Seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Generate_Seq")
    private Long id;
    private String firstName;
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    private String phoneNo;
    private LocalDate dob;
    private String gender;
    private String address;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "parent")
    @JsonIgnore
    private List<Student> students;

    public void add(Student student){
        students = new ArrayList<>();
        if(student != null)
            students.add(student);
    }

}
