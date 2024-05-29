
//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024
package AllExceptions;
/**
 * This file contains the implementation of the BadNameException class,
 * which represents an exception thrown when a movie names is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie names.
 * 
 */
public class BadNameException extends Exception{
	/**
	 * Default constructor
	 */
	public BadNameException() {
		super();
	}
	/**
	 * Constructor with parameter
	 * @param message
	 */
	public BadNameException(String message) {
		super(message);
	}
}
