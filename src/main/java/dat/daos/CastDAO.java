package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Cast;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class CastDAO extends BaseDAO<Cast> {
    // Additional methods specific to Cast can be added here

    private EntityManagerFactory emf;

    public CastDAO() {
        this.emf = HibernateConfig.getEntityManagerFactory("mytmdb");
    }

    public List<Cast> findMoviesByActor(String actorName) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Cast> query = em.createQuery(
                    "SELECT c FROM Cast c WHERE LOWER(c.name) = LOWER(:actorName)", Cast.class);
            query.setParameter("actorName", actorName);
            return query.getResultList();
        }
    }

    public List<Object[]> findMoviesByActor2(String actorName) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Object[]> query = em.createQuery(
                    "SELECT c, m FROM Cast c JOIN c.movie m WHERE LOWER(c.name) = LOWER(:actorName)", Object[].class);
            query.setParameter("actorName", actorName);
            return query.getResultList();
        }
    }
}