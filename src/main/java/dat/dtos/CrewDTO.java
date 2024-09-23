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
public class CrewDTO {
    private boolean adult;
    private int gender;
    private int id;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    private String originalName;
    private double popularity;
    private String profilePath;
    private String creditId;
    private String department;
    private String job;

    public static List<CrewDTO> convertFromJsonCrewList(String jsonText) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            MovieResponseDTO creditsResponse = objectMapper.readValue(jsonText, MovieResponseDTO.class);
            return creditsResponse.getCrew();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}