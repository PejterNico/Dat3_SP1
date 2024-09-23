package dat.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"ProductionCompany\"")
public class ProductionCompany {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String logoPath;
    private String name;
    private String origin_country;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
