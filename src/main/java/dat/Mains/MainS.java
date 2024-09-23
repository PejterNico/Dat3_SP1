package dat.Mains;

import dat.daos.CastDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.entities.Cast;
import dat.entities.Genre;
import dat.entities.Movie;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainS {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        long startTime = System.currentTimeMillis();
        Locale.setDefault(Locale.US);

        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        CastDAO castDAO = new CastDAO();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("Choose an option:");
            System.out.println("1. Retrieve all movies");
            System.out.println("2. Retrieve all genres");
            System.out.println("3. Retrieve movies by genre");
            System.out.println("4. Retrieve movies by actor");
            System.out.println("5. Retrive movie info, genre, cast and crew by movie id");
            System.out.println("6. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    List<Movie> allMovies = movieDAO.findAll(Movie.class);
                    allMovies.forEach(System.out::println);
                    break;
                case 2:
                    List<Genre> allGenres = genreDAO.findAll(Genre.class);
                    allGenres.forEach(System.out::println);
                    break;
                case 3:
                    System.out.print("Enter genre name: ");
                    String genreName = scanner.nextLine();
                    List<Genre> genres = genreDAO.findGenresByName(genreName);
                    genres.forEach(System.out::println);
                    break;
                case 4:
                    System.out.print("Enter actor name: ");
                    String actorName = scanner.nextLine();
                    List<Object[]> moviesWithActor = castDAO.findMoviesByActor2(actorName);
                    moviesWithActor.forEach(result -> {
                        Cast cast = (Cast) result[0];
                        Movie movie = (Movie) result[1];
                        System.out.println("Actor: " + cast.getName() + ", Character: " + cast.getCharacter() + ", Original Title: " + movie.getOriginalTitle());
                    });
                    break;
                case 5:
                    System.out.print("Enter movie id: ");
                    int movieId = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    Movie movie = movieDAO.findById(Movie.class, movieId);
                    System.out.println(movie);
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");
    }
}
