package io.github.vishvakalhara.handymanbackend.error_handling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
public class ErrorController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e){

        log.error(e.getMessage());
        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("An unexpected error occured!")
                .build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgsException(IllegalArgumentException e){

        ApiErrorResponse error = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationErrors(MethodArgumentNotValidException e){

        List<ApiErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(err -> {
            fieldErrors.add(new ApiErrorResponse.FieldError(err.getField(), err.getDefaultMessage()));
        });

        ApiErrorResponse error = ApiErrorResponse.builder().status(HttpStatus.BAD_REQUEST.value())
                .message("Validation Error")
                .errors(fieldErrors).build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiErrorResponse> handleAppErrors(AppException e){

        ApiErrorResponse err = ApiErrorResponse
                .builder()
                .status(e.getStatus().value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(err, e.getStatus());
    }

//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiErrorResponse> handleBadCredentialsExceptions(BadCredentialsException e){
//
//        ApiErrorResponse err = ApiErrorResponse
//                .builder()
//                .status(HttpStatus.UNAUTHORIZED.value())
//                .message("Incorrect username or password")
//                .build();
//
//        return new ResponseEntity<>(err, HttpStatus.UNAUTHORIZED);
//    }
}
