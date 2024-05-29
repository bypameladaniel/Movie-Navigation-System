import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import AllExceptions.BadDurationException;
import AllExceptions.BadGenreException;
import AllExceptions.BadNameException;
import AllExceptions.BadRatingException;
import AllExceptions.BadScoreException;
import AllExceptions.BadTitleException;
import AllExceptions.BadYearException;
import AllExceptions.ExcessFieldsException;
import AllExceptions.MissingFieldsException;
import AllExceptions.MissingQuotesException;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;

/**
 * The main class that orchestrates the execution of the movie categorization process.
 * It reads input files, performs categorization and validation, and writes output files.
 * Then you can nagivate through the movie records
 */
public class Main {
	private static final char fieldDelimiter = ',';
	private static final int ARRAY_SIZE = 17;
	
	/**
     * The main entry point of the program.
     * 
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String part1_manifest = "part1_manifest.txt";
		
		String[] inputFiles = {
				"Movies1990.csv",
				"Movies1991.csv",
				"Movies1992.csv",
				"Movies1993.csv",
				"Movies1994.csv",
				"Movies1995.csv",
				"Movies1996.csv",
				"Movies1997.csv",
				"Movies1998.csv",
				"Movies1999.csv",
		};
		
		PrintWriter myWriter = null;
		try {
			myWriter = new PrintWriter(new FileOutputStream("part1_manifest.txt"));
			for(String file: inputFiles) {
				myWriter.println(file);
			}
			System.out.println("part1_manifest.txt file was created successfully.");
			myWriter.close();
		} catch (FileNotFoundException fnf) {
			System.out.println("Error while creating the manifest file.");
		}
		
		
		String part2_manifest = do_part1(part1_manifest);
		try {
			String part3_manifest = do_part2(part2_manifest);
			do_part3(part3_manifest);
		} catch (MissingQuotesException e) {
			System.out.println("Didnt properly handle exceptions in part1");
		} catch (MissingFieldsException e) {
			System.out.println("Didnt properly handle exceptions in part1");
		} catch (ExcessFieldsException e) {
			System.out.println("Didnt properly handle exceptions in part1");
		}
		
		
		
		
	}
    
    /**
     * Deserializes movies from files specified in the part3 manifest, displays a movie menu,
     * and allows navigation through movies.
     * 
     * @param part3_manifest the name of the part3 manifest file
     */
    private static void do_part3(String part3_manifest) {
    	Movie[][] allMovies = deserializeMovies(part3_manifest);
    	movieMenu(allMovies);
		
	}
    /**
     * Displays a movie menu and allows navigation through movies.
     * 
     * @param allMovies a 2D array containing Movie objects categorized by genre
     */

    private static void movieMenu(Movie[][] allMovies) {
        Scanner scanner = new Scanner(System.in);
        int currentArrayIndex = 0;
        int currentPosition = 0;

        while (true) {
            System.out.println("----------------------------------");
            System.out.println("            Main Menu             ");
            System.out.println("----------------------------------");
            System.out.println("s  Select a movie array to navigate");
            System.out.println("n  Navigate musical movies (" + allMovies[0].length + " records)");
            System.out.println("x  Exit");
            System.out.print("Enter Your Choice: ");

            String choice = scanner.next();
            if (choice.equalsIgnoreCase("x")) {
                System.out.println("Goodbye!");
                break;
            } else if (choice.equalsIgnoreCase("n")) {
                // Navigate musical movies
                currentArrayIndex = 0;
                currentPosition = 0;
                System.out.println("----------------------------------");
                System.out.println("Navigating musical movies (0)");
                System.out.println("Enter Your Choice: ");
                int choice2 = scanner.nextInt();
                if (allMovies[0].length == 0 && choice2 > 0) {
                	System.out.println("EOF has been reached.");
                    System.out.println("No movie.");
                    continue; // Go back to the main menu
                }
                if (allMovies[0].length == 0 && choice2 < 0) {
                	System.out.println("BOF has been reached.");
                    System.out.println("No movie.");
                    continue; // Go back to the main menu
                }
                if (choice2 == 0) {
                    break;
                }
                if (choice2 > 0) {
                    // Navigating forward
                    int absChoice = Math.abs(choice2);
                    int startIndex = currentPosition + 1;
                    int endIndex = Math.min(allMovies[currentArrayIndex].length - 1, currentPosition + absChoice - 1);
                    if (endIndex == allMovies[currentArrayIndex].length - 1) {
                        System.out.println("EOF has been reached.");
                    }
                    Movie currentMovie = allMovies[currentArrayIndex][currentPosition];
                    System.out.println("Current Movie: " + currentMovie);
                    for (int i = startIndex; i <= endIndex; i++) {
                        Movie movie = allMovies[currentArrayIndex][i];
                        System.out.println(i + ": " + movie);
                    }
                    currentPosition = Math.max(currentPosition, endIndex);
                } else {
                    // Navigating backward
                    int absChoice = Math.abs(choice2);
                    int startIndex = Math.max(0, currentPosition - absChoice);
                    int endIndex = currentPosition - 1;
                    if (startIndex == 0) {
                        System.out.println("BOF has been reached.");
                    }
                    Movie currentMovie = allMovies[currentArrayIndex][currentPosition];
                    System.out.println("Current Movie: " + currentMovie);
                    for (int i = endIndex; i >= startIndex; i--) {
                        Movie movie = allMovies[currentArrayIndex][i];
                        System.out.println(i + ": " + movie);
                    }
                    currentPosition = Math.min(currentPosition, startIndex);
                }
            } else if (choice.equalsIgnoreCase("s")) {
                // Select a movie array
                System.out.println("----------------------------------");
                System.out.println("          Genre Sub-Menu          ");
                System.out.println("----------------------------------");
                for (int i = 0; i < ARRAY_SIZE; i++) {
                    if (allMovies[i] != null) {
                        System.out.println((i + 1) + " " + getGenreName(i) + " (" + allMovies[i].length + " movies)");
                    }
                }
                System.out.println("18 Exit");
                System.out.println("----------------------------------");
                System.out.print("Enter Your Choice: ");
                int arrayChoice = scanner.nextInt();
                if (arrayChoice == 18) {
                    System.out.println("Goodbye!");
                    break;
                }
                if (arrayChoice >= 1 && arrayChoice <= ARRAY_SIZE) {
                    currentArrayIndex = arrayChoice - 1;
                    currentPosition = 0;
                } else {
                    System.out.println("Invalid choice!");
                }
            } else {
                System.out.println("Invalid choice!");
            }

            // Navigate movies
            while (true) {
                System.out.println("----------------------------------");
                System.out.println("Navigating " + getGenreName(currentArrayIndex) + " movies (" + allMovies[currentArrayIndex].length + " records)");
                System.out.println("Enter your choice: ");
                int choice3 = scanner.nextInt();
                if (allMovies[currentArrayIndex].length == 0 && choice3 > 0) {
                	System.out.println("EOF has been reached.");
                    System.out.println("No movie.");
                    break; // Go back to the main menu
                }
                if (allMovies[currentArrayIndex].length == 0 && choice3 < 0) {
                	System.out.println("BOF has been reached.");
                    System.out.println("No movie.");
                    break; // Go back to the main menu
                }

                if (choice3 == 0) {
                    break;
                }
                if (choice3 > 0) {
                    // Navigating forward
                    int absChoice = Math.abs(choice3);
                    int startIndex = currentPosition + 1;
                    int endIndex = Math.min(allMovies[currentArrayIndex].length - 1, currentPosition + absChoice - 1);
                    
                    if (endIndex == allMovies[currentArrayIndex].length - 1) {
                        System.out.println("EOF has been reached.");
                    }
                    Movie currentMovie = allMovies[currentArrayIndex][currentPosition];
                	System.out.println("Current Movie: " + currentMovie);
                    for (int i = startIndex; i <= endIndex; i++) {
                        Movie movie = allMovies[currentArrayIndex][i];
                        System.out.println((i+1) + ": " + movie);
                    }
                    currentPosition = Math.max(currentPosition, endIndex);
                }if(choice3 < 0){
                	int absChoice = Math.abs(choice3);
                	int startIndex = Math.max(0, currentPosition - absChoice);
                	int endIndex = Math.max(0, currentPosition - 1); // Corrected endIndex calculation
                	
                	if (startIndex == 0) {
                	    System.out.println("BOF has been reached.");
                	}
                	Movie currentMovie = allMovies[currentArrayIndex][currentPosition];
                	System.out.println("Current Movie: " + currentMovie);
                	for (int i = endIndex; i >= startIndex; i--) { // Adjusted iteration direction
                	    Movie movie = allMovies[currentArrayIndex][i];
                	    if(currentMovie != allMovies[currentArrayIndex][i])
                	    System.out.println((i+1) + ": " + movie);
                	}
                	currentPosition = Math.min(currentPosition, startIndex);
                }
            }
        }
    }

    /**
     * Retrieves the genre name based on the given index.
     * 
     * @param index the index of the genre
     * @return the name of the genre
     */
    private static String getGenreName(int index) {
        String[] genres = {"musical", "comedy", "animation", "adventure", "drama", "crime", "biography",
                "horror", "action", "documentary", "fantasy", "mystery", "sci-fi", "family", "western",
                "romance", "thriller"};
        return genres[index];
    }
	
    /**
     * Finds the index of the given genre in the genres array.
     * 
     * @param genre the genre to search for
     * @return the index of the genre if found, otherwise -1
     */
    private static int findGenreIndex(String genre) {
    	
        String[] genres = {"musical", "comedy", "animation", "adventure", "drama", "crime", "biography",
                "horror", "action", "documentary", "fantasy", "mystery", "sci-fi", "family", "western",
                "romance", "thriller"};
        for (int i = 0; i < genres.length; i++) {
            if (genres[i].equalsIgnoreCase(genre)) {
                return i;
            }
        }
        return -1; // Genre not found
    }
    
    /**
     * Deserializes movies from files specified in the part3 manifest and categorizes them by genre.
     * 
     * @param part3_manifest the name of the part3 manifest file
     * @return a 2D array containing Movie objects categorized by genre
     */
	private static Movie[][] deserializeMovies(String part3_manifest) {
	
        Movie[][] allMovies = new Movie[ARRAY_SIZE][];
        BufferedReader part3_manifestREADER = null;
        try {
        	part3_manifestREADER  = new BufferedReader(new FileReader(part3_manifest));
        	String line;
            int index = 0 ;
            while ((line = part3_manifestREADER.readLine())!=null) {
                
                ObjectInputStream inputStream = null;
                try {
                	inputStream = new ObjectInputStream(new FileInputStream(line));
                	// Read the serialized movies
                    Movie[] movies = (Movie[]) inputStream.readObject();
                    // Find the index of the genre in the genres array
                    String genre = getGenreName(index);
                    int genreIndex = findGenreIndex(genre); // Implement this method
                    // Assign the movies to the corresponding genre index in allMovies
                    allMovies[genreIndex] = movies;
                    
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Incountered a ClassNotFoundException or a IOException");
                    
                }
                index++;
            }
        } catch (FileNotFoundException e) {
           System.out.println("Cound not find the part3_manifest.txt");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return allMovies;
    }
	
	/**
	 * Reads a manifest file containing movie records and categorizes them based on genre,
	 * performing validation on each record. Writes valid records to separate genre files
	 * and logs invalid records to a bad movie records file.
	 * 
	 * @param part1_manifest the filename of the manifest file containing movie records
	 * @return the filename of the part 2 manifest file generated
	 */
	private static String do_part1(String part1_manifest){
    	
		BufferedReader part1_manifestREADER = null;
		
		PrintWriter writeGenreFiles = null;
		PrintWriter writeComedy= null;
		PrintWriter writeAction= null;
		PrintWriter writeBiography= null;
		PrintWriter writeMusical= null;
		PrintWriter writeAdventure= null;
		PrintWriter writeAnimation= null;
		PrintWriter writeDrama= null;
		PrintWriter writeCrime = null;
		PrintWriter writeHorror= null;
		PrintWriter writeFantasy= null;
		PrintWriter writeMystery= null;
		PrintWriter writeDocumentary= null;
		PrintWriter writeFamily= null;
		PrintWriter writeWestern = null;
		PrintWriter writeSciFi= null;
		PrintWriter writeThriller= null;
		PrintWriter writeRomance= null;
		
		PrintWriter badFile = null;
	
		try {
			
			badFile = new PrintWriter(new FileOutputStream("bad_movie_records.csv"));
			part1_manifestREADER = new BufferedReader(new FileReader(part1_manifest));
			String fileName;
			while((fileName = part1_manifestREADER.readLine())!= null) {
				BufferedReader insideMovieFilesREADER = null;
				try {
					insideMovieFilesREADER = new BufferedReader(new FileReader(fileName));
					String line;
					int lineCount = 0;
					while((line = insideMovieFilesREADER.readLine())!= null) {
						lineCount++;
						String[] tokens;
						try {
							boolean isNotValid = false;
							tokens = getFields(line, 10);
							String yearToken = tokens[0].trim();
				            String titleToken = tokens[1];
				            String durationToken = tokens[2];
				            String genreToken = tokens[3].trim();
				            String ratingToken = tokens[4];
				            String scoreToken = tokens[5].trim();
				            String directorToken = tokens[6];
				            String actor1Token = tokens[7];
				            String actor2Token = tokens[8];
				            String actor3Token = tokens[9];
				            int duration = 0;
				            int year = 0;
				            double score = 0;

				            // Validate tokens
				            try {
                                year = Integer.parseInt(yearToken);
                                if (!isValidYear(year)) {
                                	isNotValid = true;
                                    throw new BadYearException();
                                }
                            } catch (NumberFormatException e) {
                            	isNotValid = true;
                                badFile.println( "Error type: Semantic (Invalid year)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                                
                                
                            } catch (BadYearException e) {
                            	badFile.println( "Error type: Semantic (Invalid year)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                                
                            }

                            // Validate duration
                            try {
                            	if(durationToken.isEmpty()) {
                            		isNotValid = true;
                            	}
                                duration = Integer.parseInt(durationToken);
                                if (!isValidDuration(duration) ) {
                                	isNotValid = true;
                                    throw new BadDurationException();
                                }
                            } catch (NumberFormatException e) {
                            	badFile.println( "Error type: Semantic (Invalid duration)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                                
                            } catch (BadDurationException e) {
                            	badFile.println( "Error type: Semantic (Invalid duration)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                            }

                            // Validate score
                            try {
                            	if(scoreToken.isEmpty()) {
                            		isNotValid = true;
                            	}
                                score = Double.parseDouble(scoreToken);
                                if (!isValidScore(score)) {
                                	isNotValid = true;
                                    throw new BadScoreException();
                                }
                            } catch (NumberFormatException e) {
                            	badFile.println( "Error type: Semantic (Invalid score)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                                
                            } catch (BadScoreException e) {
                            	badFile.println( "Error type: Semantic (Invalid score)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                            }

                            // Validate title
                            try {
                            	if (titleToken.isEmpty()) {
                            		isNotValid = true;
                            		throw new BadTitleException();
                            	}
                            }catch(BadTitleException bte) {
                            	badFile.println( "Error type: Semantic (Invalid title)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                            }
                            try {
                            if (ratingToken.isEmpty() || !isValidRating(ratingToken)) {
                            	isNotValid = true;
                            	throw new BadRatingException();
                                
                            }
                            }catch(BadRatingException bre) {
                            	badFile.println( "Error type: Semantic (Invalid rating)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                            }

                            // Validate director and actors
                            try {
                            if (directorToken.isEmpty() || actor1Token.isEmpty() || actor2Token.isEmpty() || actor3Token.isEmpty()) {
                            	isNotValid = true;
                            	throw new BadNameException();
                                
                            }
                            }catch(BadNameException bne) {
                            	badFile.println( "Error type: Semantic (Invalid name)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                            }
                            try {
                      
                                if (!isValidGenre(genreToken)) {
                                	isNotValid = true;
                                    throw new BadGenreException();
                                }
                            }catch(BadGenreException bge) {
                            	badFile.println( "Error type: Semantic (Invalid genre)");
                                badFile.println( "Original line : " + line);
                                badFile.println( "File : " + fileName);
                                badFile.println( "Line #" + lineCount);
                                badFile.println();
                                
                                
                             }
                            if(isNotValid == false) {
				                	
				                		writeWestern = new PrintWriter(new FileOutputStream("Western.csv"));
				                		writeThriller = new PrintWriter(new FileOutputStream("Thriller.csv"));
				                		writeSciFi = new PrintWriter(new FileOutputStream("Sci-Fi.csv"));
				                		writeRomance = new PrintWriter(new FileOutputStream("Romance.csv"));
				                		writeMusical = new PrintWriter(new FileOutputStream("Musical.csv"));
				                		writeMystery = new PrintWriter(new FileOutputStream("Mystery.csv"));

				                    if (genreToken.equalsIgnoreCase("Comedy")) {
				                        if (writeComedy == null) {
				                            writeComedy = new PrintWriter(new FileOutputStream("Comedy.csv"));
				                        }
				                        writeComedy.println(line);
				                    } else if (genreToken.equalsIgnoreCase("Action")) {
				                        if (writeAction == null) {
				                            writeAction = new PrintWriter(new FileOutputStream("Action.csv"));
				                        }
				                        writeAction.println(line);
				                    } else if (genreToken.equalsIgnoreCase("Biography")) {
				                        if (writeBiography == null) {
				                            writeBiography = new PrintWriter(new FileOutputStream("Biography.csv"));
				                        }
				                        writeBiography.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Adventure")) {
				                        if (writeAdventure == null) {
				                            writeAdventure = new PrintWriter(new FileOutputStream("Adventure.csv"));
				                        }
				                        writeAdventure.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Animation")) {
				                    	if(writeAnimation== null) {
				                    		writeAnimation = new PrintWriter(new FileOutputStream("Animation.csv"));
				                    	}
				                    	writeAnimation.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Drama")) {
				                        if (writeDrama == null) {
				                            writeDrama = new PrintWriter(new FileOutputStream("Drama.csv"));
				                        }
				                        writeDrama.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Crime")) {
				                        if (writeCrime == null) {
				                            writeCrime = new PrintWriter(new FileOutputStream("Crime.csv"));
				                        }
				                        writeCrime.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Horror")) {
				                        if (writeHorror == null) {
				                            writeHorror = new PrintWriter(new FileOutputStream("Horror.csv"));
				                        }
				                        writeHorror.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Fantasy")) {
				                        if (writeFantasy == null) {
				                            writeFantasy = new PrintWriter(new FileOutputStream("Fantasy.csv"));
				                        }
				                        writeFantasy.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Documentary")) {
				                        if (writeDocumentary == null) {
				                            writeDocumentary = new PrintWriter(new FileOutputStream("Documentary.csv"));
				                        }
				                        writeDocumentary.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Family")) {
				                        if (writeFamily == null) {
				                            writeFamily = new PrintWriter(new FileOutputStream("Family.csv"));
				                        }
				                        writeFamily.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Western")) {
				                        writeWestern.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Sci-Fi")) {
				                    	writeSciFi.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Thriller")) {
				                        writeThriller.println(line);
				                    }else if (genreToken.equalsIgnoreCase("Romance")) {
				                    	writeRomance.println(line);
				                    }else if(genreToken.equalsIgnoreCase("Mystery")) {
				                    	writeMystery.println(line);
				                    }else if(genreToken.equalsIgnoreCase("Musical")) {
				                    	writeMusical.println(line);
				                    }
                            }
				                    
				               
				          // Movie movie = new Movie(year, titleToken, duration, genreToken, ratingToken, score, directorToken, actor1Token, actor2Token, actor3Token);
				         
						}catch(MissingQuotesException e) {
							
							badFile.println("Error type: Syntax (Missing Quotes)");
							badFile.println("Original line: " +line);
							badFile.println("File: " + fileName);
							badFile.println("Line #" + lineCount);
							badFile.println();
							badFile.flush();
							
						}catch(MissingFieldsException e) {
							
							badFile.println("Error type: Syntax (Missing Fields)");
							badFile.println("Original line: " +line);
							badFile.println("File: " + fileName);
							badFile.println("Line #" + lineCount);
							badFile.println();
							badFile.flush();
							
							
						}catch(ExcessFieldsException e) {
							
							badFile.println("Error type: Syntax (Excess Fields)");
							badFile.println("Original line: " +line);
							badFile.println("File: " + fileName);
							badFile.println("Line #" + lineCount);
							badFile.println();
							badFile.flush();
							
						}catch(FileNotFoundException fnf) {
	            			System.out.println("Error while creating the genre files.");
	            		}
						
						
  
			       }
				}catch(FileNotFoundException e) {
					System.out.println(fileName + " file was not found.");
				}catch(IOException e) {
					System.out.println("Error reading from " + fileName + " file.");
				}
				
			}
		}catch(FileNotFoundException e) {
			System.out.println("part1_manifest.txt file was not found.");
		}catch(IOException e) {
			System.out.println("Error reading from part1_manifest.txt file.");
		}
		
		badFile.close();
		if(writeComedy !=null)
			writeComedy.close();
		if(writeAction!=null)
			writeAction.close();
		if(writeBiography!=null)
			writeBiography.close();
		if(writeAnimation!=null)
			writeAnimation.close();
		if(writeAdventure!=null)
			writeAdventure.close();
		if(writeDrama!=null)
			writeDrama.close();
		if(writeCrime!=null)
			writeCrime.close();
		if(writeHorror!=null)
			writeHorror.close();
		if(writeFantasy!=null)
			writeFantasy.close();
		if(writeMystery!=null)
			writeMystery.close();
		if(writeDocumentary!=null)
			writeDocumentary.close();
		if(writeFamily!=null)
			writeFamily.close();
		if(writeWestern!=null)
			writeWestern.close();
		if(writeSciFi!=null)
			writeSciFi.close();
		if(writeThriller!=null)
			writeThriller.close();
		if(writeRomance!=null)
			writeRomance.close();
		
    	PrintWriter part2_manifestWRITER = null;
           String[] genreFiles = {
        		   "musical.csv",
        		   "comedy.csv",
        		   "animation.csv",
        		   "adventure.csv",
        		   "drama.csv",
        		   "crime.csv",
        		   "biography.csv",
        		   "horror.csv",
        		   "action.csv",
        		   "documentary.csv",
        		   "fantasy.csv",
        		   "mystery.csv",
        		   "sci-fi.csv",
        		   "family.csv",
        		   "western.csv",
        		   "romance.csv",
        		   "thriller.csv"
   				};
           try {
   			part2_manifestWRITER  = new PrintWriter(new FileOutputStream("part2_manifest.txt"));
   			for(String file: genreFiles) {
   				part2_manifestWRITER.println(file);
   			}
   			part2_manifestWRITER.close();
   			System.out.println("part2_manifest.txt file was created successfully.");
   		} catch (FileNotFoundException fnf) {
   			System.out.println("Error while creating the part2 manifest file.");
   		}
    	

		return "part2_manifest.txt";
	}
    
	
	/**
	 * Splits a line of CSV data into fields and returns an array of fields.
	 * 
	 * @param line the line of CSV data to be parsed
	 * @param numberOfFieldsExpected the expected number of fields in the CSV data
	 * @return an array of strings representing the fields of the CSV data
	 * @throws MissingQuotesException if missing quotes are detected in the CSV data
	 * @throws MissingFieldsException if the number of fields in the CSV data is less than expected
	 * @throws ExcessFieldsException if the number of fields in the CSV data is more than expected
	 */
	private static String[] getFields(String line, int numberOfFieldsExpected) throws MissingQuotesException, MissingFieldsException, ExcessFieldsException{
        String[] fields; //1991,"Freddy's Dead, The Final Nightmare",93,Comedy,R,4.9,Rachel Talalay,Johnny Depp,Tom Arnold,Yaphet Kotto
        int numberOfFields = 1;
        boolean isInQuotes = false;
        for(int i=0; i<line.length();i++) {
            if(line.charAt(i) == '\"') {
                isInQuotes = !isInQuotes;
                continue;
            }
            if(line.charAt(i) == fieldDelimiter && !isInQuotes) {
                numberOfFields++;
            }
        }
        if (line.charAt(line.length() - 1) == fieldDelimiter) {
            numberOfFields--;
        }
        if(isInQuotes) {
            System.out.println("Missing quotes");
            throw new MissingQuotesException();
        }
        if(numberOfFields < numberOfFieldsExpected) {
        	
        	throw new MissingFieldsException();
        }else if(numberOfFields > numberOfFieldsExpected) {
        	
        	throw new ExcessFieldsException();
        }
        fields = new String[numberOfFields];
        isInQuotes = false;
        int currentFieldNumber = 0;
        fields[0] = "";
        for (int i = 0; i < line.length(); i++) {
            if(line.charAt(i) == '\"') {
                isInQuotes = !isInQuotes;
                continue;
            }
            if(line.charAt(i) == fieldDelimiter && !isInQuotes) {
                fields[currentFieldNumber] = fields[currentFieldNumber].trim();
                currentFieldNumber++;
                if (currentFieldNumber == numberOfFields) {
                    return fields;
                }
                fields[currentFieldNumber] = "";
                continue;
            }
            fields[currentFieldNumber]+=line.charAt(i);
        }
        return fields;
    }


    /**
     * Checks if the given genre is valid.
     * 
     * @param genre the genre to be validated
     * @return true if the genre is valid, false otherwise
     */
    private static boolean isValidGenre(String genre) {
		return genre.equalsIgnoreCase("musical") || genre.equalsIgnoreCase("comedy") ||
                genre.equalsIgnoreCase("animation") || genre.equalsIgnoreCase("adventure") ||
                genre.equalsIgnoreCase("drama") || genre.equalsIgnoreCase("crime") ||
                genre.equalsIgnoreCase("biography") || genre.equalsIgnoreCase("horror") ||
                genre.equalsIgnoreCase("action") || genre.equalsIgnoreCase("documentary") ||
                genre.equalsIgnoreCase("fantasy") || genre.equalsIgnoreCase("mystery") ||
                genre.equalsIgnoreCase("sci-fi") || genre.equalsIgnoreCase("family") ||
                genre.equalsIgnoreCase("western") || genre.equalsIgnoreCase("romance") ||
                genre.equalsIgnoreCase("thriller");
	}
    
    /**
     * Checks if the given year is valid.
     * 
     * @param year the year to be validated
     * @return true if the year is valid, false otherwise
     */
	private static boolean isValidYear(int year) {
        return (year >= 1900 && year <= 1999);
	}
	
	/**
	 * Checks if the given duration is valid.
	 * 
	 * @param duration the duration to be validated
	 * @return true if the duration is valid, false otherwise
	 */
	private static boolean isValidDuration(double duration) {
		return(duration >= 30 && duration <= 300); 
	}
	
	/**
	 * Checks if the given score is valid.
	 * 
	 * @param score the score to be validated
	 * @return true if the score is valid, false otherwise
	 */
	private static boolean isValidScore(double score) {
		return (score <= 10 && score >= 0);
	}
	
	/**
	 * Checks if the given rating is valid.
	 * 
	 * @param rating the rating to be validated
	 * @return true if the rating is valid, false otherwise
	 */
	private static boolean isValidRating(String rating) {
		boolean isValid = false;
		String[] validRatings = {"PG","Unrated","G","R","PG-13","NC-17"};
		
		for(String ratings: validRatings) {
			if(rating.equals(ratings)) {
				isValid = true;
			}
		}
		return isValid;
	}
	
	/**
	 * Reads movie records from CSV files specified in the part2 manifest, serializes them,
	 * and writes the serialized file names to part3 manifest.
	 * 
	 * @param part2_manifest the name of the part2 manifest file
	 * @return the name of the part3 manifest file
	 * @throws MissingQuotesException if missing quotes are detected in the CSV data
	 * @throws MissingFieldsException if the number of fields in the CSV data is less than expected
	 * @throws ExcessFieldsException if the number of fields in the CSV data is more than expected
	 */

	private static String do_part2(String part2_manifext) throws MissingQuotesException, MissingFieldsException, ExcessFieldsException {
		BufferedReader reader = null;
        PrintWriter manifestWriter = null;

        try {
            reader = new BufferedReader(new FileReader("part2_manifest.txt"));
            manifestWriter = new PrintWriter(new FileOutputStream("part3_manifest.txt"));

            String line;
            while ((line = reader.readLine()) != null) {
                String csvFileName = line;
                String serializedFileName = csvFileName.replace(".csv", ".ser");
                
                
                Movie[] movies = readMoviesFromCSV(csvFileName);
                serializeMovies(movies, serializedFileName);
                manifestWriter.println(serializedFileName);
            }

            System.out.println("part3_manifest.txt file was created successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("Error: Manifest file not found.");
        } catch (IOException e) {
            System.out.println("Error reading from manifest file.");
        } finally {
            try {
                if (reader != null)
                    reader.close();
                if (manifestWriter != null) {
                    manifestWriter.close();
                }
            } catch (IOException e) {
                System.out.println("Error closing resources.");
            }
        }
        return "part3_manifest.txt";
    }
	
	/**
	 * Serializes an array of Movie objects and saves them to a file.
	 * 
	 * @param movies the array of Movie objects to be serialized
	 * @param fileName the name of the file to save the serialized data
	 */
	private static void serializeMovies(Movie[] movies, String fileName) {
		ObjectOutputStream outputStream = null;
        try {
        	outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        
            outputStream.writeObject(movies);
            
        
        } catch (IOException e) {
            System.out.println("Error serializing data to file: " + fileName);
        }finally {
        	if(outputStream!=null) {
        		try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        	}
        }
    }
	
	/**
	 * Reads movie records from a CSV file, creates Movie objects, and returns an array of Movie objects.
	 * 
	 * @param fileName the name of the CSV file to read movie records from
	 * @return an array of Movie objects read from the CSV file
	 * @throws MissingQuotesException if missing quotes are detected in the CSV data
	 * @throws MissingFieldsException if the number of fields in the CSV data is less than expected
	 * @throws ExcessFieldsException if the number of fields in the CSV data is more than expected
	 */
	private static Movie[] readMoviesFromCSV(String fileName) throws MissingQuotesException, MissingFieldsException, ExcessFieldsException {
	    Movie[] movies = new Movie[0]; 
	    BufferedReader reader = null;
	    try {
	    reader = new BufferedReader(new FileReader(fileName));
	        String line;
	        int numMovies = 0; 
	        while ((line = reader.readLine()) != null) {
	            
	            String[] tokens = getFields(line, 10);
	            if (tokens.length == 10) {
	                
	                int year = Integer.parseInt(tokens[0].trim());
	                String title = tokens[1].trim();
	                int duration = Integer.parseInt(tokens[2].trim());
	                String genre = tokens[3].trim();
	                String rating = tokens[4].trim();
	                double score = Double.parseDouble(tokens[5].trim());
	                String director = tokens[6].trim();
	                String actor1 = tokens[7].trim();
	                String actor2 = tokens[8].trim();
	                String actor3 = tokens[9].trim();
	                
	                Movie movie = new Movie(year, title, duration, genre, rating, score, director, actor1, actor2, actor3);
	                movies = increaseArraySize(movies, numMovies + 1);
	                movies[numMovies] = movie;
	                numMovies++;
	            } else {
	                System.out.println("Invalid movie record found in " + fileName + ": " + line);
	            }
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("File not found: " + fileName);
	    } catch (IOException e) {
	        System.out.println("Error reading from file: " + fileName);
	    } catch (NumberFormatException e) {
	        System.out.println("Error parsing data from file: " + fileName);
	    }finally {
            try {
                if (reader != null)
                    reader.close();
               
            
            } catch (IOException e) {
                System.out.println("Error closing resources.");
            }
	    }
	    
	    return movies;
	}

	/**
	 * Increases the size of the given array to the specified newSize.
	 * 
	 * @param movies the array to be resized
	 * @param newSize the new size of the array
	 * @return a new array with the specified size containing the elements of the original array
	 */
	private static Movie[] increaseArraySize(Movie[] movies, int newSize) {
	    Movie[] newArray = new Movie[newSize];
	    for (int i = 0; i < movies.length; i++) {
	        newArray[i] = movies[i];
	    }
	    return newArray;
	}
}
