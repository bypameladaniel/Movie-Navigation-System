
/**
 * This file contains the implementation of the BadDurationException class,
 * which represents an exception thrown when a movie duration is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie durations.
 * 
 */
package AllExceptions;

public class BadDurationException extends Exception{
	/**
	 * Default constructor
	 */
	public BadDurationException() {
		super();
	}
	
	/**
	 * Constructor with specific parameter
	 * @param message
	 */
	public BadDurationException(String message) {
		super(message);
	}
}
