package persistence.sql.ddl.query;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import persistence.sql.QueryTranslator;
import persistence.sql.ddl.entity.Person4;

@DisplayName("4단계 요구사항 - @Entity, @Table(schema), @Id, @Column, @Transient 어노테이션을 바탕으로 drop 쿼리 만들어보기")
class QueryBuilder4Test {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(QueryBuilder4Test.class);

    private final Class<?> entityClass = Person4.class;

    private final QueryTranslator queryBuilder = new QueryTranslator();

    @Test
    @DisplayName("@Entity, @Table(schema), @Id, @Column, @Transient 어노테이션을 바탕으로 drop 쿼리 만들어보기")
    void buildDropQuery() {
        String dropQuery = queryBuilder.getDropTableQuery(entityClass);

        log.debug("Drop query: {}", dropQuery);

        assertThat(dropQuery).isEqualTo("DROP TABLE schema.users");
    }
}
