package dat.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    //private String overview;
    private String releaseDate;
    private double voteAverage;
    private int voteCount;
    private String originalLanguage;
    private String originalTitle;
    private String backdropPath;
    private String posterPath;
    private boolean adult;
    private boolean video;
    private double popularity;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Cast> castList = new ArrayList<>();

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Crew> crewList = new ArrayList<>();

    @OneToMany(mappedBy ="movie", cascade = CascadeType.ALL)
    private List<Genre> genreList = new ArrayList<>();


    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", voteAverage=" + voteAverage +
                ", voteCount=" + voteCount +
                ", originalLanguage='" + originalLanguage + '\'' +
                ", originalTitle='" + originalTitle + '\'' +
                ", backdropPath='" + backdropPath + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", adult=" + adult +
                ", video=" + video +
                ", popularity=" + popularity +
                '}';
    }
}