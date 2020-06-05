package com.votes.config.exception;

import com.votes.config.exception.enumerator.ErrorCodeEnum;
import org.springframework.http.HttpStatus;

public class UserVoteException extends RuntimeException {

    private HttpStatus httpStatus;
    private ErrorCodeEnum errorCode;

    public UserVoteException(HttpStatus httpStatus, ErrorCodeEnum errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
}
