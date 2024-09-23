package dat.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"SpokenLanguge\"")
public class SpokenLanguage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String english_name;
    private String iso_639_1;
    private String name;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
