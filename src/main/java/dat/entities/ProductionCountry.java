package dat.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"ProductionCountry\"")
public class ProductionCountry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String iso_3166_1;
    private String name;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
