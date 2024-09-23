package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Data;

import java.io.IOException;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CastDTO {
    private boolean adult;
    private int gender;
    private int id;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    private String originalName;
    private double popularity;
    private String profilePath;
    private int castId;
    private String character;
    private String creditId;
    private int order;

    public static List<CastDTO> convertFromJsonCastList(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO creditsResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return creditsResponse.getCast();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
