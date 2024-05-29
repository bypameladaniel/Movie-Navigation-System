
import java.io.Serializable;
/**
 * Represents a movie with various attributes such as year, title, duration, genre, rating, score, director, and actors.
 * This class implements the Serializable interface to support serialization.
 */
public class Movie implements Serializable{
	private static final long serialVersionUID = 1L;
	private int year;
	private String title;
	private int duration;
	private String genre;
	private String rating;
	private double score;
	private String director;
	private String actor1;
	private String actor2;
	private String actor3;
	
	/**
     * Default constructor for creating a Movie object with default values.
     */
	public Movie() {
	}
	
	/**
     * Constructs a Movie object with specified attributes.
     * 
     * @param year the year the movie was released
     * @param title the title of the movie
     * @param duration the duration of the movie in minutes
     * @param genre the genre of the movie
     * @param rating the rating of the movie
     * @param score the score of the movie
     * @param director the director of the movie
     * @param actor1 the first actor of the movie
     * @param actor2 the second actor of the movie
     * @param actor3 the third actor of the movie
     */
	public Movie(int year, String title, int duration, String genre, String rating, double score, String director,
			String actor1, String actor2, String actor3) {
		super();
		this.year = year;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.rating = rating;
		this.score = score;
		this.director = director;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.actor3 = actor3;
	}

	/**
     * Returns a string representation of the Movie object.
     * 
     * @return a string representation of the Movie object
     */
	@Override
	public String toString() {
		return "Movie [year=" + year + ", title=" + title + ", duration=" + duration + ", genre=" + genre + ", rating="
				+ rating + ", score=" + score + ", director=" + director + ", actor1=" + actor1 + ", actor2=" + actor2
				+ ", actor3=" + actor3 + "]";
	}
	
	/**
     * Compares this Movie object to the specified object for equality.
     * 
     * @param obj the object to compare with
     * @return true if the specified object is equal to this Movie object, false otherwise
     */
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movie other = (Movie) obj;
		return actor1.equals(other.actor1) && actor2.equals(other.actor2)
				&& actor3.equals(other.actor3) && director.equals(other.director)
				&& duration == other.duration && genre.equals(other.genre)
				&& rating.equals(other.rating)
				&& score == other.score
				&& title.equals(other.title) && year == other.year;
	}
	
}
