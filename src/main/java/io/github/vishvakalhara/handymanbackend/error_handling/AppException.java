package io.github.vishvakalhara.handymanbackend.error_handling;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    private final HttpStatus status;

    public AppException(String message) {
        super(message);
        this.status = HttpStatus.BAD_REQUEST;
    }

    public AppException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
