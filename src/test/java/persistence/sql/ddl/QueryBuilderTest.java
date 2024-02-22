package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.converter.EntityConverter;
import persistence.sql.converter.TypeMapper;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.entity.Person;
import persistence.sql.model.NotEntityException;
import persistence.sql.model.Table;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[Unit] Query Builder Test")
class QueryBuilderTest {

    private final EntityConverter entityConverter = new EntityConverter(new TypeMapper());
    private final QueryBuilder queryBuilder = new QueryBuilder(new H2Dialect());

    @DisplayName("Table 타입의 인스턴스를 전달하면 해당 인스터슨의 정보를 바탕으로 테이블을 생성하는 쿼리를 반환한다.")
    @Test
    void build_create_query_success() {
        Table table = entityConverter.convertEntityToTable(Person.class);

        String query = queryBuilder.buildCreateQuery(table);

        assertThat(query.startsWith("CREATE TABLE " + table.getName())).isTrue();
    }

    @DisplayName("Table 타입의 인스턴스를 전달하면 해당 인스터슨의 정보를 바탕으로 테이블을 제거하는 쿼리를 반환한다.")
    @Test
    void build_drop_query_success() {
        Table table = entityConverter.convertEntityToTable(Person.class);

        String query = queryBuilder.buildDropQuery(table);

        assertThat(query.equals("DROP TABLE " + table.getName())).isTrue();
    }

}
