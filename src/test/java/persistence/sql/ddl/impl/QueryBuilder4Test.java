package persistence.sql.ddl.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import persistence.sql.ddl.entity.Person4;
import persistence.sql.ddl.QueryBuilder;

@DisplayName("4단계 요구사항 - @Entity, @Table(schema), @Id, @Column, @Transient 어노테이션을 바탕으로 drop 쿼리 만들어보기")
class QueryBuilder4Test {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryBuilder4Test.class);

    private final Class<?> entityClass = Person4.class;

    private final QueryBuilder queryBuilder = new DefaultQueryBuilder();

    @Test
    @DisplayName("@Entity, @Table(schema), @Id, @Column, @Transient 어노테이션을 바탕으로 drop 쿼리 만들어보기")
    void buildDropQuery() {
        String dropQuery = queryBuilder.buildDropQuery(entityClass);

        log.debug("Drop query: {}", dropQuery);

        assertThat(dropQuery).isEqualTo("DROP TABLE schema.users");
    }
}
