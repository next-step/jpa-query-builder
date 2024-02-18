package persistence.sql.ddl;

import domain.Person;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.sql.QueryBuilder;

class CreateQueryBuilderTest {

    @DisplayName("Person Entity로 테이블 생성 ddl이 만들어지는지 확인한다.")
    @Test
    void builder() {
        QueryBuilder builder = CreateQueryBuilder.getInstance();

        String ddl = builder.builder(Person.class);

        assertThat(ddl).isEqualTo("CREATE TABLE users (id BIGINT AUTO_INCREMENT PRIMARY KEY,nick_name VARCHAR,old INTEGER,email VARCHAR NOT NULL)");
    }
}
