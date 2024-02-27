package com.shah.springjwttutorials.exception;

import com.shah.springjwttutorials.pojo.dto.MyResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author NORUL
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MyException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyResponse<String> handleMyException(HttpServletRequest req, MyException e) {
        String errorMessages = e.getErrorMessage();
        log.error("requestUrl : {}, occurred an error : {}", req.getRequestURI(), errorMessages);
        return MyResponse.failureResponse(errorMessages);
    }

    /**
     * Handles MethodArgumentNotValidException thrown by Spring.
     *
     * @param req the HttpServletRequest object
     * @param e   the MethodArgumentNotValidException object
     * @return a MyResponse object with an error message
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyResponse<List<Errors>> handleMethodArgumentNotValidException(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUri = req.getRequestURI();

        List<Errors> cause = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> Errors.builder()
                        .fieldName(error.getField())
                        .errorMessage(error.getDefaultMessage())
                        .build())
                .toList();

        log.error("requestUrl : {}, occurred an error : {}", req.getRequestURI(), cause);
        return MyResponse.failureResponse(cause, "Validation failed for request " +
                "URI: " + requestUri);
    }

    /**
     * Handles ExpiredJwtException thrown by Spring Security.
     *
     * @param req the HttpServletRequest object
     * @param e   the ExpiredJwtException object
     * @return a MyResponse object with an error message
     */
    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyResponse<String> handleExpiredJwtException(HttpServletRequest req, ExpiredJwtException e) {
        String errorMessages = e.getLocalizedMessage();
        log.error("requestUrl : {}, occurred an error : {}", req.getRequestURI(), errorMessages);
        return MyResponse.failureResponse(errorMessages);
    }

    /**
     * Handles BadCredentialsException thrown by Spring Security.
     *
     * @param req the HttpServletRequest object
     * @param e   the BadCredentialsException object
     * @return a MyResponse object with an error message
     */
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MyResponse<String> handleBadCredentialsException(HttpServletRequest req, BadCredentialsException e) {
        String errorMessages = e.getLocalizedMessage();
        log.error("requestUrl : {}, occurred an error : {}", req.getRequestURI(), errorMessages);
        return MyResponse.failureResponse(errorMessages);
    }

    /**
     * Handles all unexpected exceptions thrown by the application.
     *
     * @param req the HttpServletRequest object
     * @param e   the Exception object
     * @return a MyResponse object with an error message
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public MyResponse<String> handleBaseException(HttpServletRequest req, Exception e) {
        String errorMessages = e.getLocalizedMessage();
        log.error("requestUrl : {}, occurred an error : {}", req.getRequestURI(), errorMessages);
        return MyResponse.failureResponse(errorMessages);
    }
}
