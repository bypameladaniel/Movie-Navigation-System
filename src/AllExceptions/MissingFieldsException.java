
/**
 * This file contains the implementation of the MissingFieldsException class,
 * which represents an exception thrown when a movie has missing fields.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie fields.
 * 
 */
package AllExceptions;

public class MissingFieldsException extends Exception{
	/**
	 * Default constructor
	 */
	public MissingFieldsException() {
		super();
	}
	/**
	 * Constructor with parameter
	 * @param message
	 */
	public MissingFieldsException(String message) {
		super(message);
	}
}
