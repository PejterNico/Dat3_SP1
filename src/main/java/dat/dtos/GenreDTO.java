package dat.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class GenreDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("name")
    private String name;

    public static List<GenreDTO> convertFromJsonCastList(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO creditsResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return creditsResponse.getGenres();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
