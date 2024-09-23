package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class MovieDAO extends BaseDAO<Movie> {
    // Additional methods specific to Movie can be added here

    private EntityManager em;
    private EntityManagerFactory emf;

    public MovieDAO() {
        this.emf = HibernateConfig.getEntityManagerFactory("mytmdb");
    }


    public List<Movie> findAllWithGenres() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m JOIN FETCH m.genreList", Movie.class);
        return query.getResultList();
    }

    public Movie findMovieWithDetailsById(int movieId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Movie> query = em.createQuery(
                    "SELECT m FROM Movie m " +
                            "LEFT JOIN FETCH m.genreList " +
                            "LEFT JOIN FETCH m.castList " +
                            "LEFT JOIN FETCH m.crewList " +
                            "WHERE m.id = :movieId", Movie.class);
            query.setParameter("movieId", movieId);
            return query.getSingleResult();
        }
    }



}

