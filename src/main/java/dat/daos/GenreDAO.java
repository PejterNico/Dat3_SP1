package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Genre;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class GenreDAO extends BaseDAO {
    // Additional methods specific to Genre can be added here
    private EntityManagerFactory emf;

    public GenreDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Movie> findMoviesByGenre(String genreName) {
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Genre g JOIN g.movie m WHERE g.name = :genreName", Movie.class);
            query.setParameter("genreName", genreName);
            return query.getResultList();
        }
    }
}