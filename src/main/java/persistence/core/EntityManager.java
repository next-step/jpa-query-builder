package persistence.core;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;
import persistence.criteria.CriteriaQuery;

public interface EntityManager {
    public void persist(Object entity);

    public void remove(Object entity);

    public void flush();

    public void clear();

    public Query createQuery(String qlString);

    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery);

    public EntityManagerFactory getEntityManagerFactory();

    public CriteriaBuilder getCriteriaBuilder();

}
