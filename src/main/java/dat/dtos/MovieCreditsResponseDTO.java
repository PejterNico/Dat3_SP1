package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreditsResponseDTO {
    private List<CastDTO> cast;
    private List<CrewDTO> crew;
    private List<GenreDTO> genres;
}