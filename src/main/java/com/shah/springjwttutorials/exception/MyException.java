package com.shah.springjwttutorials.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author NORUL
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class MyException extends RuntimeException {

    private final String errorMessage;
}
