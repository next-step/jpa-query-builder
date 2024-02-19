package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.Person;

import static org.assertj.core.api.Assertions.assertThat;


class QueryBuilderTest {

    @DisplayName("Person 클래스의 DDL을 생성한다.")
    @Test
    void createDdl() {

        QueryBuilder queryBuilder = new QueryBuilder();
        String ddl = queryBuilder.createDdl(Person.class);

        assertThat(ddl).isEqualTo("create table person ( id bigint, name varchar (255), age integer, primary key (id))");
    }
}
