package dat.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class SpokenLanguageDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("english_name")
    private String english_name;
    @JsonProperty("iso_639_1")
    private String iso_639_1;
    @JsonProperty("name")
    private String name;

    public static List<SpokenLanguageDTO> convertFromJsonCastList(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO creditsResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return creditsResponse.getSpokenLanguages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

