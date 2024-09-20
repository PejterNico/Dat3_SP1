package dat.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "\"Cast\"")
public class Cast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String character;
    @Column(name = "\"order\"")
    private int order;
    private String profilePath;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Override
    public String toString() {
        return "Cast{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", character='" + character + '\'' +
                '}';
    }
}