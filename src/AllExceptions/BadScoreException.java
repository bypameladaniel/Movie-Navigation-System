//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024

/**
 * This file contains the implementation of the BadScoreException class,
 * which represents an exception thrown when a movie score is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie scores.
 * 
 */
package AllExceptions;

public class BadScoreException extends Exception{
	/**
	 * Default constructor
	 */
	public BadScoreException() {
		super();
	}
	/**
	 * Constructor with paramater
	 * @param message
	 */
	public BadScoreException(String message) {
		super(message);
	}
}
