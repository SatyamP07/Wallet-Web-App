package com.wallet.app.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(WalletExceptionHandler.class);
	
	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<String> noUserFoundExceptionErrorMessage(UserNotFoundException exception) {
		logger.error("Exception occured NoUserFoundException");
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({IncorrectPasswordException.class})
	public ResponseEntity<String> incorrectPasswordExceptionErrorMessage(IncorrectPasswordException exception) {
		logger.error("Exception occured IncorrectPasswordException");
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
