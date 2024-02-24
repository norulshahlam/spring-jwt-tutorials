package com.shah.springjwttutorials.exception;

import com.shah.springjwttutorials.dto.MyResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * For all other unexpected exceptions
     *
     * @param req
     * @param e
     * @return
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleBaseException(HttpServletRequest req, Exception e) {
        String errorMessages = e.getLocalizedMessage();
        log.error("requestUrl : {}, occurred an error : {}", req.getRequestURI(), errorMessages);
        MyResponse failureResponse = MyResponse.failureResponse(errorMessages);
        return ResponseEntity.badRequest().body(failureResponse);
    }
}
