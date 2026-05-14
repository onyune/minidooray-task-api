package com.nhnacademy.minidoorayteam9taskapi.exception;

public class ProjectUserAlreadyExistException extends RuntimeException {
    public ProjectUserAlreadyExistException(String message) {
        super(message);
    }
}
