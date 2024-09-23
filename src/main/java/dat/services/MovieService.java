package dat.services;

import dat.daos.MovieDAO;
import dat.dtos.*;
import dat.entities.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

public class MovieService {

    private static final String API_KEY = System.getenv("api_key");  // Replace with your TMDb API Key
    private static final String BASE_URL_MOVIE = "https://api.themoviedb.org/3/movie/";
    private static final String BASE_URL_DISCOVER = "https://api.themoviedb.org/3/discover/movie";

    public static String getMovieById(int id) throws IOException, InterruptedException {

        String url = BASE_URL_MOVIE + id + "?api_key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getMoviesByAvgVotingBetween(double min, double max, int page) throws IOException, InterruptedException {
        String url = String.format("%s?api_key=%s&page=%d&sort_by=popularity.desc&vote_average.gte=%.2f&vote_average.lte=%.2f", BASE_URL_DISCOVER, API_KEY, page, min, max);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public static String getAllDanishMoviesFromRelaseYearAndCountry(String min, String max, int page) throws IOException, InterruptedException {
        String url = String.format("%s?api_key=%s&page=%d&release_date.gte=%s&release_date.lte=%s&sort_by=popularity.desc&with_origin_country=DK", BASE_URL_DISCOVER, API_KEY, page, min, max);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }


    public static String getMovieCreditsById(int id) throws IOException, InterruptedException {
        String url = BASE_URL_MOVIE + id + "/credits?api_key=" + API_KEY;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public void saveMovieWithDetails(int fetchMovieId) {
        //One movie at a time
        // VIRKER- Save movie and the cast and crew and genres in the database
        //int fetchMovieId = 1136350;
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

            // Save production companies
            List<ProductionCompanyDTO> productionCompanyList = ProductionCompanyDTO.convertFromJsonCastList(jsonTextMifune);
            if (productionCompanyList != null) {
                productionCompanyList.forEach(productionCompany -> {
                    ProductionCompany productionCompany1ntity = new ProductionCompany();
                    productionCompany1ntity.setId(productionCompany.getId());
                    productionCompany1ntity.setLogoPath(productionCompany.getLogo_path());
                    productionCompany1ntity.setName(productionCompany.getName());
                    productionCompany1ntity.setOrigin_country(productionCompany.getOrigin_country());
                    productionCompany1ntity.setMovie(movieEntity);
                    movieEntity.getProductionCompanies().add(productionCompany1ntity);
                });
            } else {
                System.out.println("No production companies found");
            }
            // Save production companies
            List<ProductionCountryDTO> productionCountriesList = ProductionCountryDTO.convertFromJsonCastList(jsonTextMifune);
            if (productionCompanyList != null) {
                productionCountriesList.forEach(productionCountry -> {
                    ProductionCountry productionCountry1ntity = new ProductionCountry();
                    productionCountry1ntity.setId(productionCountry.getId());
                    productionCountry1ntity.setIso_3166_1(productionCountry.getIso_3166_1());
                    productionCountry1ntity.setName(productionCountry.getName());
                    productionCountry1ntity.setMovie(movieEntity);
                    movieEntity.getProductionCountries().add(productionCountry1ntity);
                });
            } else {
                System.out.println("No production countries found");
            }

            // Save spoken languages
            List<SpokenLanguageDTO> spokenLanguageList = SpokenLanguageDTO.convertFromJsonCastList(jsonTextMifune);
            if (productionCompanyList != null) {
                spokenLanguageList.forEach(spokenLanguage -> {
                    SpokenLanguage spokenLanguage1ntity = new SpokenLanguage();
                    spokenLanguage1ntity.setId(spokenLanguage.getId());
                    spokenLanguage1ntity.setIso_639_1(spokenLanguage.getIso_639_1());
                    spokenLanguage1ntity.setName(spokenLanguage.getName());
                    spokenLanguage1ntity.setMovie(movieEntity);
                    movieEntity.getSpokenLanguages().add(spokenLanguage1ntity);
                });
            } else {
                System.out.println("No spoken languages found");
            }
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
    }


   public void saveDanishMoviesFiveYears(int totalPages) throws IOException, InterruptedException {
       // Fetch all Danish movies, cast and crew from the last five years and save them to the database (And you can set Page number)
       //int totalPages = 1; // Set the total number of pages
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
   }
}


