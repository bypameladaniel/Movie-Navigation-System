//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024

/**
 * This file contains the implementation of the MissingQuotesException class,
 * which represents an exception thrown when a movie has missing quotes fields.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie quotes.
 * 
 */
package AllExceptions;

public class MissingQuotesException extends Exception {
	/**
	 * Default constructor
	 */
	public MissingQuotesException() {
		super();
	}
	/**
	 * Constructor with parameter
	 * @param message
	 */
	public MissingQuotesException(String message) {
		super(message);
	}
}
