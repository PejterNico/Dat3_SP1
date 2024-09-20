package dat.daos;

import java.util.List;
import dat.config.HibernateConfig;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class BaseDAO<T> {
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("mytmdb");

    public T create(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    public T findById(Class<T> clazz, int id) {
        EntityManager em = emf.createEntityManager();
        T entity = em.find(clazz, id);
        em.close();
        return entity;
    }

    public T update(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T mergedEntity = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return mergedEntity;
    }

    public void delete(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
        em.close();
    }

    public List<T> findAll(Class<T> clazz) {
        EntityManager em = emf.createEntityManager();
        List<T> entities = em.createQuery("SELECT e FROM " + clazz.getSimpleName() + " e", clazz)
                .getResultList();
        em.close();
        return entities;
    }

    public T merge(T entity) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        T mergedEntity = em.merge(entity);
        em.getTransaction().commit();
        em.close();
        return mergedEntity;
    }
}