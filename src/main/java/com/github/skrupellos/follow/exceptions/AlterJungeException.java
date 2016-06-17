package com.github.skrupellos.follow.exceptions;

/**
 * Thrown when user tries to use {@link String}s that would fuck up the programs output.
 * 
 * @author Alexander Koos
 * @author Felix Buchdrucker
 */
public class AlterJungeException extends RuntimeException {

	private static final long serialVersionUID = -9177735973712160950L;

	public AlterJungeException() {
		this("Aufs Maul -> Bämm!!!");
	}

	public AlterJungeException(String message) {
		super(message);
	}

	public AlterJungeException(Throwable cause) {
		super(cause);
	}

	public AlterJungeException(String message, Throwable cause) {
		super(message, cause);
	}

	public AlterJungeException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
