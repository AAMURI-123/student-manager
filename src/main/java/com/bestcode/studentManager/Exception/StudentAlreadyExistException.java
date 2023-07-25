package com.bestcode.studentManager.Exception;

public class StudentAlreadyExistException extends RuntimeException{

    public StudentAlreadyExistException(String message) {
        super(message);
    }
}
