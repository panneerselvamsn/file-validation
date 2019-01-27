package com.file.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	@ExceptionHandler(UnsupportedFormatException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public @ResponseBody ExceptionResponse handleResourceNotFound(final UnsupportedFormatException exception) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}
	
	@ExceptionHandler(BadRequestException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public @ResponseBody ExceptionResponse handleResourceNotFound(final BadRequestException exception) {
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorMessage(exception.getMessage());
		return error;
	}
	
}
