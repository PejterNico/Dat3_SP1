package dat.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
public class ProductionCompanyDTO {
    @JsonProperty("id")
    private int id;
    @JsonProperty("logo_path")
    private String logo_path;
    @JsonProperty("name")
    private String name;
    @JsonProperty("origin_country")
    private String origin_country;

    public static List<ProductionCompanyDTO> convertFromJsonCastList(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO creditsResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return creditsResponse.getProductionCompanie();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
