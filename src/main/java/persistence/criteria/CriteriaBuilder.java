package persistence.criteria;

import jakarta.persistence.criteria.CriteriaQuery;

public interface CriteriaBuilder {

    CriteriaQuery<Object> createQuery();

    <T> CriteriaQuery<T> createQuery(Class<T> resultClass);
}
