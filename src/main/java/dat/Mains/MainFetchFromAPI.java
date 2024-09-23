/*package dat;

import dat.dtos.CastDTO;
import dat.dtos.CrewDTO;
import dat.dtos.MovieDTO;
import dat.services.MovieService;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainFetchFromAPI {
    public static void main(String[] args) {
        // Record the start time
        long startTime = System.currentTimeMillis();

        // Set locale to US to avoid issues with date formatting and number formatting
        Locale.setDefault(Locale.US);

        try {
            // Fetch all Danish movies from the specified release year and country
            String jsonTextM = MovieService.getAllDanishMoviesFromRelaseYearAndCountry("2019-09-01", "2024-09-01", 1);
            List<MovieDTO> allDKMoviesFiveYear = MovieDTO.convertFromJsonList(jsonTextM);
            allDKMoviesFiveYear.forEach(movie -> {
                System.out.println(movie);

                // Fetch and print the cast for each movie
                try {
                    String jsonTextCreditsCast = MovieService.getMovieCreditsById(movie.getId());
                    List<CastDTO> castList = CastDTO.convertFromJsonCastList(jsonTextCreditsCast);
                    castList.forEach(System.out::println);

                    // Fetch and print the crew for each movie
                    String jsonTextCreditsCrew = MovieService.getMovieCreditsById(movie.getId());
                    List<CrewDTO> crewList = CrewDTO.convertFromJsonCrewList(jsonTextCreditsCrew);
                    crewList.forEach(System.out::println);
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        // Record the end time of the proragm and fething data
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Task runtime: " + duration + " milliseconds");
    }
}
*/