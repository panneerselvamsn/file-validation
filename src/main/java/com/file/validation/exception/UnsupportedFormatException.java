package com.file.validation.exception;

public class UnsupportedFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedFormatException() {
		super();
	}
    
    public UnsupportedFormatException(final String message) {
		super(message);
	}
}
