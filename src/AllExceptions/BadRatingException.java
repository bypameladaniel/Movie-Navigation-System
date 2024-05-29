//Pamela Daniel 40286602
//COMP249
//Assignment#2
//Due date: Wednesday March 27th 2024

package AllExceptions;
/**
 * This file contains the implementation of the BadRatingException class,
 * which represents an exception thrown when a movie rating is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie ratings.
 * 
 */
public class BadRatingException extends Exception{
	/**
	 * Default constructor
	 */
	public BadRatingException() {
		super();
	}
	/**
	 * Constructor with parameter
	 * @param message
	 */
	public BadRatingException(String message) {
		super(message);
	}
}
