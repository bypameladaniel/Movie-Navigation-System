//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024

/**
 * This file contains the implementation of the BadTitleException class,
 * which represents an exception thrown when a movie title is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie titles.
 * 
 */
package AllExceptions;

public class BadTitleException extends Exception{
	/**
	 * Default constructor
	 */
	public BadTitleException() {
		super();
	}
	/**
	 * Constructor with parameter
	 * @param message
	 */
	public BadTitleException(String message) {
		super(message);
	}
}
