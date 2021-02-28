import java.util.List;
import java.util.Scanner;

public class Frontend {
	private BackendDummy backend;
	private Scanner input = new Scanner(System.in);

	public void run(BackendDummy backend) {
		this.backend = backend;
	}

	/**
	 * Frontend interface for picking movie genres
	 */
	public void genreMode() {
		List<String> allGenres = backend.getAllGenres();

		// Intro / how to use
		System.out.println("----------------------------------------------------------------");
		System.out.println("| To select/unselect a genre, type the number corresponding to  |");
		System.out.println("| the genre you wish to select/deselect.                        |");
		System.out.println("----------------------------------------------------------------");
		System.out.println();
		

		// input options
		for (int i = 0; i < allGenres.size(); i++) {

			// genre to be printed
			String genre = allGenres.get(i);

			// Prints genre along with corresponding index
			System.out.print(String.valueOf(i+1) + ". ");
			System.out.print(genre);

			if (backend.getGenres().contains(genre)) 
				System.out.print(" ----> SELECTED");
			else
				System.out.print(" ----> ");
			System.out.println();
		}
		System.out.println();
		System.out.println("To return to the base mode, type the \"x\" key");
		System.out.println();


		while (input.hasNext() && input.hasNextInt()) {

			try {
				// Determines genre by what number was entered
				String selectedGenre = allGenres.get(input.nextInt()-1);

				if (backend.getGenres().contains(selectedGenre)) {
					// Removes genre if selected
					backend.removeGenre(selectedGenre);
				} else {
					// Adds genre if not selected
					backend.addGenre(selectedGenre);
				}

				// Reloads options
				genreMode();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.out.println("ERROR: Please select a number 1 - " + allGenres.size());
			}
		}

		while (input.hasNext() && input.hasNextLine()) {
			String key = input.nextLine();

			if (key.equals("x")) baseMode();
		}

	}

	public void ratingMode() {

		// Intro / how to use
		System.out.println("----------------------------------------------------------------");
		System.out.println("| To select/unselect a rating, type the number corresponding   |");
		System.out.println("| to the range of ratings you wish to select/unselect.         |");
		System.out.println("----------------------------------------------------------------\n");
		
		// Input options
		for (int i = 0; i < 10; i++)  {
			// converts i + 1 to String
			String numAsString = String.valueOf(i+1);

			// Prints Input key
			System.out.print(numAsString + ": ");
			// Prints corresponding rating
			System.out.print(numAsString + " - " + String.valueOf(i+1.999f));

			// Indicator based on whether or not rating is selected
			// for (String rating : backend.getAvgRatings()) {
				
			// 	if (rating.substring(0, 1).equals(numAsString)) System.out.print(" ----> SELECTED");

			// }
			System.out.println();
		}
		System.out.println();

		while (input.hasNext() && input.hasNextInt()) {

			if (input.nextInt() < 10) {
				String numAsString = String.valueOf(input.nextInt());
				// boolean isSelected = false;

				for (String rating : backend.getAvgRatings()) {
				
					if (rating.substring(0, 1).equals(numAsString)) {
						//isSelected = true;
						backend.removeAvgRating(rating);
					} 
		
				}

				// if (!isSelected) backend.addAvgRating(numAsString);

				ratingMode();
			}
		}

		while (input.hasNext() && input.hasNextLine()) {
			String key = input.nextLine();

			if (key.equals("x")) baseMode();
		}
	}

	/**
	 * Default interface for movie mapper (think home screen)
	 */
	public void baseMode() {
	
		// Welcome message
		System.out.println("----------------------------------------------------------------");
		System.out.println("|               WELCOME TO THE CS400 MOVIE MAPPER!             |");
		System.out.println("----------------------------------------------------------------\n");


		System.out.println("Top 3 Movies: \n");
		if (backend != null) {
			// List of top 3 movies (by average rating)
			List<MovieInterface> movieList = backend.getThreeMovies(0);

			// iterates through list of movies
			for (int i = 0; i < movieList.size(); i++) {
				System.out.println((i+1) + ". " + movieList.get(i).getTitle());
			}
		} 
		System.out.println();

		while (input.hasNext()) {
			String key = input.nextLine();

			if (key.equals("x")) break;

			if (key.equals("g")) genreMode();

			if (key.equals("r")) ratingMode();
		}
		System.out.println("Quiting...");
		System.exit(0);
	}

	public static void main(String[] args) {

		//BackendDummy dummyBackend = new BackendDummy(args);

		Frontend frontend = new Frontend();
		frontend.run(new BackendDummy(args));

		frontend.baseMode();
	}
}