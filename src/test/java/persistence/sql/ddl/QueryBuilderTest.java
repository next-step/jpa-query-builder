package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.dialect.H2Dialect;
import persistence.sql.entity.Person;
import persistence.sql.model.NotEntityException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("[Unit] Query Builder Test")
class QueryBuilderTest {

    private final QueryBuilder queryBuilder = new QueryBuilder(new H2Dialect());

    @DisplayName("@Entity 어노테이션이 추가된 클래스 타입을 전달할 경우 해당 테이블을 생성하는 쿼리를 반환한다.")
    @Test
    void build_create_query_success() {
        String query = queryBuilder.buildCreateQuery(Person.class);

        assertThat(query.startsWith("CREATE TABLE")).isTrue();
    }

    @DisplayName("@Entity 어노테이션이 적용되지 않은 클래스 타입을 전달할 경우 예외를 반환한다.")
    @Test
    void build_create_query_fail_by_not_entity() {

        assertThatThrownBy(() -> queryBuilder.buildCreateQuery(String.class))
            .isInstanceOf(NotEntityException.class)
            .hasMessage("해당 클래스는 Entity가 아닙니다.");

    }

}
