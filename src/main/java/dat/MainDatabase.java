package dat;

import dat.daos.BaseDAO;
import dat.daos.MovieDAO;
import dat.dtos.CastDTO;
import dat.dtos.CrewDTO;
import dat.dtos.GenreDTO;
import dat.dtos.MovieDTO;
import dat.entities.Cast;
import dat.entities.Crew;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.services.MovieService;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class MainDatabase {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Record the start time
        long startTime = System.currentTimeMillis();

        // Set locale to US to avoid issues with date formatting and number formatting
        Locale.setDefault(Locale.US);


        //One movie at a time
        // VIRKER- Save movie and the cast and crew and genres in the database
        int fetchMovieId = 1079201;
        try {
            String jsonTextMifune = MovieService.getMovieById(fetchMovieId);
            MovieDTO mifuneDTO = MovieDTO.convertFromJson(jsonTextMifune);

            // Save movie to database
            Movie movieEntity = new Movie();
            movieEntity.setAdult(mifuneDTO.isAdult());
            movieEntity.setBackdropPath(mifuneDTO.getBackdropPath());
            movieEntity.setOriginalLanguage(mifuneDTO.getOriginalLanguage());
            movieEntity.setOriginalTitle(mifuneDTO.getOriginalTitle());
            //movieEntity.setOverview(mifuneDTO.getOverview());
            movieEntity.setPopularity(mifuneDTO.getPopularity());
            movieEntity.setPosterPath(mifuneDTO.getPosterPath());
            movieEntity.setTitle(mifuneDTO.getTitle());
            movieEntity.setVideo(mifuneDTO.isVideo());
            movieEntity.setVoteAverage(mifuneDTO.getVoteAverage());
            movieEntity.setVoteCount(mifuneDTO.getVoteCount());
            movieEntity.setReleaseDate(String.valueOf(mifuneDTO.getRelease_date()));

            // Fetch and save genres
            List<GenreDTO> genreList = GenreDTO.convertFromJsonCastList(jsonTextMifune);
            genreList.forEach(genre -> {
                Genre genreEntity = new Genre();
                genreEntity.setId(genre.getId());
                genreEntity.setName(genre.getName());
                genreEntity.setMovie(movieEntity);
                movieEntity.getGenreList().add(genreEntity);
            });

            // Fetch and save cast
            String jsonTextCreditsCast = MovieService.getMovieCreditsById(fetchMovieId);
            List<CastDTO> castList = CastDTO.convertFromJsonCastList(jsonTextCreditsCast);
            castList.forEach(cast -> {
                Cast castEntity = new Cast();
                castEntity.setName(cast.getName());
                castEntity.setCharacter(cast.getCharacter());
                castEntity.setMovie(movieEntity);
                movieEntity.getCastList().add(castEntity);
            });

            // Fetch and save crew
            String jsonTextCreditsCrew = MovieService.getMovieCreditsById(fetchMovieId);
            List<CrewDTO> crewList = CrewDTO.convertFromJsonCrewList(jsonTextCreditsCrew);
            crewList.forEach(crew -> {
                Crew crewEntity = new Crew();
                crewEntity.setName(crew.getName());
                crewEntity.setJob(crew.getJob());
                crewEntity.setMovie(movieEntity);
                movieEntity.getCrewList().add(crewEntity);
            });

            MovieDAO movieDAO = new MovieDAO();
            movieDAO.update(movieEntity);
            System.out.println(movieEntity);
            System.out.println(genreList.stream().map(GenreDTO::getName).collect(Collectors.toList()));
            System.out.println("Movie saved to database: " + mifuneDTO.getOriginalTitle());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Fetch all Danish movies, cast and crew from the last five years and save them to the database (And you can set Page number)
        int totalPages = 1; // Set the total number of pages
        for (int page = 1; page <= totalPages; page++) {
            String jsonTextM = MovieService.getAllDanishMoviesFromRelaseYearAndCountry("2019-09-01", "2024-09-01", page);
            List<MovieDTO> allDKMoviesFiveYear = MovieDTO.convertFromJsonList(jsonTextM);
            allDKMoviesFiveYear.forEach(movie -> {
                System.out.println(movie);

                // Save movie to database
                Movie movieEntity = new Movie();
                movieEntity.setAdult(movie.isAdult());
                movieEntity.setBackdropPath(movie.getBackdropPath());
                movieEntity.setOriginalLanguage(movie.getOriginalLanguage());
                movieEntity.setOriginalTitle(movie.getOriginalTitle());
                movieEntity.setPopularity(movie.getPopularity());
                movieEntity.setPosterPath(movie.getPosterPath());
                movieEntity.setTitle(movie.getTitle());
                movieEntity.setVideo(movie.isVideo());
                movieEntity.setVoteAverage(movie.getVoteAverage());
                movieEntity.setVoteCount(movie.getVoteCount());
                movieEntity.setReleaseDate(String.valueOf(movie.getRelease_date()));

                // Fetch and save genres
                try {
                    List<GenreDTO> genreList = GenreDTO.convertFromJsonCastList(jsonTextM);
                    if (genreList != null) {
                        genreList.forEach(genre -> {
                            Genre genreEntity = new Genre();
                            genreEntity.setId(genre.getId());
                            genreEntity.setName(genre.getName());
                            genreEntity.setMovie(movieEntity);
                            movieEntity.getGenreList().add(genreEntity);
                        });
                    }

                    // Fetch and save Cast
                    String jsonTextCreditsCast = MovieService.getMovieCreditsById(movie.getId());
                    List<CastDTO> castList = CastDTO.convertFromJsonCastList(jsonTextCreditsCast);
                    castList.forEach(cast -> {
                        Cast castEntity = new Cast();
                        castEntity.setName(cast.getName());
                        castEntity.setCharacter(cast.getCharacter());
                        castEntity.setMovie(movieEntity);
                        movieEntity.getCastList().add(castEntity);
                    });

                    // Fetch and save crew
                    String jsonTextCreditsCrew = MovieService.getMovieCreditsById(movie.getId());
                    List<CrewDTO> crewList = CrewDTO.convertFromJsonCrewList(jsonTextCreditsCrew);
                    crewList.forEach(crew -> {
                        Crew crewEntity = new Crew();
                        crewEntity.setName(crew.getName());
                        crewEntity.setJob(crew.getJob());
                        crewEntity.setMovie(movieEntity);
                        movieEntity.getCrewList().add(crewEntity);
                    });
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
                MovieDAO movieDAO = new MovieDAO();
                movieDAO.update(movieEntity);
                System.out.println("Movie saved to database: " + movie.getTitle());
            });
        }
 
        /*
        //Retrive all movies from database
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> allMovies = movieDAO.findAll(Movie.class);
        // Print each movie
        allMovies.forEach(movie -> {
                    System.out.println(movie);
        });
        */

        // Record the end time
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");

    }
}