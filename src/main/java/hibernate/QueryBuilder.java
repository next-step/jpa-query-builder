package hibernate;

import hibernate.entity.EntityClass;

public interface QueryBuilder {

    String generateQuery(EntityClass entityClass);
}
