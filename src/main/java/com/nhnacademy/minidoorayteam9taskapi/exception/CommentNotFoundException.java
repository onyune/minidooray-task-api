package com.nhnacademy.minidoorayteam9taskapi.exception;

/**
 * NOT FOUND 404
 */
public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException(Long id) {
        super("ID: "+ id + "에 해당하는 코멘트를 찾을 수 없습니다.");
    }
}
