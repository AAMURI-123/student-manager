package com.bestcode.studentManager.repository;

import com.bestcode.studentManager.model.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository
public interface ParentRepository extends JpaRepository<Parent,Long> {
    Optional<Parent> findByEmail(String email);
   // Optional<Parent> findByStudents(String email);

}
