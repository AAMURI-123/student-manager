package com.bestcode.studentManager.model;

import com.bestcode.studentManager.enumratoin.Gender;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {

    public Student(String firstName, String lastName, String email, String phoneNo, LocalDate dob, String gender, Parent parent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.dob = dob;
        this.gender = gender;
        this.parent = parent;
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


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

}
