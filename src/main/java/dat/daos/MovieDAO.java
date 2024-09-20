package dat.daos;

import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MovieDAO extends BaseDAO<Movie> {
    // Additional methods specific to Movie can be added here

    private EntityManager em;
    public List<Movie> findAllWithGenres() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m JOIN FETCH m.genreList", Movie.class);
        return query.getResultList();
    }
}

