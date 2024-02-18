package persistence.sql.ddl;

import domain.Person;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryBuilder;

class DdlQueryBuilderTest {

    @DisplayName("Person Entity로 테이블 생성 ddl이 만들어지는지 확인한다.")
    @Test
    void builder() {
        QueryBuilder builder = new DdlQueryBuilder();

        String ddl = builder.builder(Person.class);

        assertThat(ddl).isEqualTo("CREATE TABLE Person (id BIGINT PRIMARY KEY,name VARCHAR,age INTEGER)");
    }
}
