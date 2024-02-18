package persistence.sql.ddl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.domain.Person;

import static org.assertj.core.api.Assertions.assertThat;

class QueryBuilderTest {

    @DisplayName("Person create ddl문 생성")
    @Test
    void persion_ddl_create() {
        final QueryBuilder queryBuilder = new QueryBuilder();
        final Class<?> personClass = Person.class;

        String actualDDL = queryBuilder.createDdl(personClass);

        String expectedDDL = "create table person (id bigint not null, age integer, name varchar(255), primary key (id))";

        assertThat(actualDDL).isEqualTo(expectedDDL);
    }
}