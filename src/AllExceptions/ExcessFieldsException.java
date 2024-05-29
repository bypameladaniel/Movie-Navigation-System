//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024

/**
 * This file contains the implementation of the ExcessFieldsException class,
 * which represents an exception thrown when a movie has excess fields.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie fields.
 * 
 */
package AllExceptions;

public class ExcessFieldsException extends Exception {
	/**
	 * Default constructor
	 */
	public ExcessFieldsException() {
		super();
	}
	/**
	 * Constructor with parameter
	 * @param message
	 */
	public ExcessFieldsException(String message) {
		super(message);
	}
}
