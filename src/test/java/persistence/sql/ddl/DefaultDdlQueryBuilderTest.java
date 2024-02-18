package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryException;
import persistence.sql.dialect.Dialect;
import persistence.sql.dialect.H2Dialect;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DefaultDdlQueryBuilderTest {

    private final Dialect dialect= new H2Dialect();
    private final DdlQueryBuilder<Object> queryBuilder = new DefaultDdlQueryBuilder<>(dialect);

    @DisplayName("PersonV1 Entity 정보로 create 쿼리 만들어보기")
    @Test
    public void buildCreateQuery() throws Exception {
        // given
        final PersonV1 person = new PersonV1(1L, "name", 20);

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

    @DisplayName("")
    @Test
    public void throwExceptionWhenBuildNotEntityCreateQuery() throws Exception {
        // given
        final PersonV0 person = new PersonV0();

        // when then
        assertThatThrownBy(() -> queryBuilder.buildCreateQuery(person))
                .isInstanceOf(QueryException.class)
                .hasMessage("PersonV0 is not entity");
    }

}