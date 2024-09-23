/*package dat.Mains;

import dat.daos.CastDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dtos.*;
import dat.entities.*;
import dat.services.MovieService;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainDatabase {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Hello World!");
        // Record the start time
        long startTime = System.currentTimeMillis();

        // Set locale to US to avoid issues with date formatting and number formatting
        Locale.setDefault(Locale.US);

// The fetching from api
        // Create an instance of the class containing the saveMovieWithDetails method
        MovieService movieService = new MovieService();

     // Save one movie with id
        //Call the saveMovieWithDetails method with the desired fetchMovieId
        int fetchMovieId = 1136350;
        movieService.saveMovieWithDetails(fetchMovieId);

        // Save all Danish movies from 5 years back
        int totalPage = 1;
        movieService.saveDanishMoviesFiveYears(totalPage);


// Kald til database

        MovieDAO movieDAO = new MovieDAO();
        GenreDAO genreDAO = new GenreDAO();
        CastDAO castDAO = new CastDAO();


        // Retrieve all movies
        List<Movie> allMovies = movieDAO.findAll(Movie.class);
        allMovies.forEach(System.out::println);


        //Retrieve all genres
        List<Genre> allGenres = genreDAO.findAll(Genre.class);
        allGenres.forEach(System.out::println);

        // Retrieve movies by genre
        List<Genre> actionMovies = genreDAO.findGenresByName("Comedy");
        actionMovies.forEach(System.out::println);

        // Retrieve movies by actor
        List<Object[]> moviesWithActor2 = castDAO.findMoviesByActor2("Nikolaj Lie Kaas");
        moviesWithActor2.forEach(result -> {
            Cast cast = (Cast) result[0];
            Movie movie = (Movie) result[1];
            System.out.println("Actor: " + cast.getName() + ", Character: " + cast.getCharacter()+ ", Original Title: " + movie.getOriginalTitle());
        });



        // Record the end time
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");

    }
}
*/