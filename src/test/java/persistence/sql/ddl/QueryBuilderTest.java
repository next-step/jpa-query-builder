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
        final Class<Person> personClass = Person.class;

        String createDdl = queryBuilder.createDdl(personClass);

        assertThat(createDdl).isEqualTo("create table person (id bigint not null, age integer, name varchar(255), primary key (id)");
    }
}