package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultDdlQueryBuilderTest {

    private Dialect dialect= new H2Dialect();

    @DisplayName("PersonV1 Entity 정보로 create 쿼리 만들어보기")
    @Test

    public void generateCreateQuery() throws Exception {
        // given
        final PersonV1 person = new PersonV1(1L, "name", 20);
        final DdlQueryBuilder<Object> queryBuilder = new DefaultDdlQueryBuilder<>(dialect);

        final String ddl = """
                CREATE TABLE PERSONV1 (
                    ID BIGINT PRIMARY KEY,
                    NAME VARCHAR(255),
                    AGE INTEGER
                );""".trim();

        // when
        final String createQuery = queryBuilder.buildCreateQuery(person);

        // then
        assertThat(createQuery).isEqualTo(ddl);
    }

}