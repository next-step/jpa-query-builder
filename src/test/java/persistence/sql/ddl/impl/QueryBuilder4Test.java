package persistence.sql.ddl.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import persistence.sql.ddl.Person4;

class QueryBuilder4Test {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryBuilder4Test.class);

    private final Class<?> entityClass = Person4.class;

    private final QueryBuilder4 queryBuilder = new QueryBuilder4();

    @Test
    void buildDropQuery() {
        String dropQuery = queryBuilder.buildDropQuery(entityClass);

        log.debug("Drop query: {}", dropQuery);

        assertThat(dropQuery).isEqualTo("DROP TABLE schema.users");
    }
}
