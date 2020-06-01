package com.wallet.app.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class WalletExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(WalletExceptionHandler.class);
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> noUserFoundExceptionErrorMessage(UserNotFoundException unf) {
		logger.error("Exception occured NoUserFoundException");
		return ResponseEntity.ok().body(unf.getMessage());
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	public ResponseEntity<String> incorrectPasswordExceptionErrorMessage(IncorrectPasswordException ipe) {
		logger.error("Exception occured IncorrectPasswordException");
		return ResponseEntity.ok().body(ipe.getMessage());
	}
}
