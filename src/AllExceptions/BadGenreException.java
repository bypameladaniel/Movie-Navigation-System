
/**
 * This file contains the implementation of the BadGenreException class,
 * which represents an exception thrown when a movie genre is invalid.
 * 
 * This class is part of the movie management system and is used to handle
 * errors related to movie genres.
 * 
 */
package AllExceptions;

public class BadGenreException extends Exception{
	/**
	 * Default constructor
	 */
	public BadGenreException() {
		super();
	}
	/**
	 * Constructor with specific attributes
	 * @param message
	 */
	public BadGenreException(String message) {
		super(message);
	}
}
