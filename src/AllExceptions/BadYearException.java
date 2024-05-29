//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024

/**
 * This file contains the implementation of the BadYearException class,
 * which represents an exception thrown when a movie year is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie years.
 * 
 */
package AllExceptions;

public class BadYearException extends Exception{
	/**
	 * Default constructor
	 */
	public BadYearException() {
		super();
	}
	/**
	 * Constructor
	 * @param message
	 */
	public BadYearException(String message) {
		super(message);
	}
}
