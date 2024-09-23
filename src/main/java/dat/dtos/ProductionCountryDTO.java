package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class ProductionCountryDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("iso_3166_1")
    private String iso_3166_1;
    @JsonProperty("name")
    private String name;

    public static List<ProductionCountryDTO> convertFromJsonCastList(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO creditsResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return creditsResponse.getProductionCountries();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

