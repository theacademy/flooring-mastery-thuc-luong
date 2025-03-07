package com.sg.flooringmastery.service;

public class FlooringDataValidationException extends RuntimeException {
    public FlooringDataValidationException(String message) {
        super(message);
    }

    public FlooringDataValidationException(String message,
                                           Throwable cause) {
    super(message, cause);
  }

}
