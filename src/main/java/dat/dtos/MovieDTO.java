package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {
    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("budget")
    private int budget;

    @JsonProperty("genres")
    private List<GenreDTO> genres;

    @JsonProperty("id")
    private int id;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("production_companies")
    private List<ProductionCompanyDTO> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountryDTO> productionCountries;

    @JsonProperty("revenue")
    private int revenue;

    @JsonProperty("runtime")
    private int runtime;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguageDTO> spokenLanguages;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("title")
    private String title;

    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("vote_count")
    private int voteCount;

    @JsonProperty("cast")
    private List<CastDTO> cast;

    private LocalDate release_date;


    public static MovieDTO convertFromJson(String jsonText) {
        // Create an instance of ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            // Convert the JSON string to MovieDTO object
            MovieDTO movie = objectMapper.readValue(jsonText, MovieDTO.class);
            return movie;
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // convertFromJson to List<MovieDTO>
    public static List<MovieDTO> convertFromJsonList(String jsonText) {
        // Create an instance of ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            // Deserialize into the MovieResponseDTO class
            MovieResponseDTO movieResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return movieResponse.getResults();
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Getter method to extract release year from release_date
    public String getReleaseYear() {
        if (release_date != null) {
            return String.valueOf(release_date.getYear());
        }
        return null;
    }
}
