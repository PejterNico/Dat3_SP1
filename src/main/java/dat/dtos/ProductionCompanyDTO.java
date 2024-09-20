package dat.dtos;

import lombok.Data;

@Data
public class ProductionCompanyDTO {
    private int id;
    private String logo_path;
    private String name;
    private String origin_country;
}
