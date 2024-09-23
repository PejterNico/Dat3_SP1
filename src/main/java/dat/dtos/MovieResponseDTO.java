package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponseDTO {
    private int page;
    private List<MovieDTO> results;  // List of MovieDTOs
    private int total_pages;
    private int total_results;
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
    private List<GenreDTO> genres;
    private List<ProductionCompanyDTO> productionCompanie;
    private List<ProductionCountryDTO> productionCountries;
    private List<SpokenLanguageDTO> spokenLanguages;
}

